package com.team.leaf.loginsession;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/session")
public class SessionManager {

    @GetMapping("/create")         // Member 사용자 정보 객체 수정 필
    public String createSession(HttpServletRequest request /*, Member loginMember*/) {
        HttpSession session = request.getSession();
        session.setAttribute("JSESSIONID",session.getId()/*, loginMember*/);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}