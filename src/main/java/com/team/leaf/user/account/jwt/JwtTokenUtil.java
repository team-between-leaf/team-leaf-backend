package com.team.leaf.user.account.jwt;

import com.team.leaf.user.account.dto.response.TokenDto;
import com.team.leaf.user.account.entity.AccountDetail;
import com.team.leaf.user.account.entity.AccountRole;
import com.team.leaf.user.account.entity.OAuth2Account;
import com.team.leaf.user.account.entity.RefreshToken;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.OAuth2Repository;
import com.team.leaf.user.account.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PrincipalDetailsService userDetailsService;
    private final AccountRepository accountRepository;
    private final OAuth2Repository oAuth2Repository;
    //private static final String AUTHORITIES_KEY = "auth";
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "refresh_token";
    private static final long ACCESS_TIME = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
    private static final long REFRESH_TIME = Duration.ofDays(14).toMillis(); // 만료시간 2주

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    public TokenDto createToken(String email) {
        Date date = new Date();
        AccountRole role = getRoleFromEmail(email);

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(date.getTime() + REFRESH_TIME))
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();


        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TIME)
                .build();
    }

    //refreshToken 검증
    public Boolean tokenValidataion(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty");
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Boolean refreshTokenValidation(String token) {

        //1차 검증
        if (!tokenValidataion(token)) return false;

        //DB에 저장된 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEmail(getEmailFromToken(token));

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();

        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    public AccountRole getRoleFromEmail(String email) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElse(null);
        OAuth2Account oAuth2Account = oAuth2Repository.findByEmail(email).orElse(null);

        if (accountDetail != null) {
            return accountDetail.getRole();
        } else if (oAuth2Account != null) {
            return oAuth2Account.getRole();
        }
        throw new UsernameNotFoundException("Not found user");

    }
}