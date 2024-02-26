package com.team.leaf.shopping.wish.controller;
import com.team.leaf.shopping.wish.dto.WishResponse;
import com.team.leaf.shopping.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @GetMapping("/product/wish/{userId}")
    public ResponseEntity wishPage(@PathVariable(name="userId") Long userId){
        return ResponseEntity.ok(wishService.findWishById(userId));
    }

    @DeleteMapping("/product/wish/{userId}")
    public ResponseEntity<Boolean> deleteWish(@PathVariable(name="userId") Long userId,
                                              @RequestParam(name="productId") Long productId){
        return ResponseEntity.ok(wishService.deleteById(userId, productId));
    }
}
