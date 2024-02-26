package com.team.leaf.shopping.wish.controller;

import com.team.leaf.shopping.wish.dto.WishAddRequest;
import com.team.leaf.shopping.wish.dto.WishRequest;
import com.team.leaf.shopping.wish.dto.WishResponse;
import com.team.leaf.shopping.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @GetMapping("/product/wish/{userId}")
    public ResponseEntity<List<WishResponse>> wishPage(@PathVariable(name="userId") Long userId){
        return ResponseEntity.ok(wishService.findWishById(userId));
    }

    @PostMapping("/product/wish")
    public ResponseEntity<Boolean> addWish(@RequestBody WishAddRequest wishAddRequest){
        return ResponseEntity.ok(wishService.addWish(wishAddRequest));
    }

    @DeleteMapping("/product/wish")
    public ResponseEntity<Boolean> deleteWish(@RequestBody WishRequest wishRequest){
        return ResponseEntity.ok(wishService.deleteById(wishRequest));
    }
}
