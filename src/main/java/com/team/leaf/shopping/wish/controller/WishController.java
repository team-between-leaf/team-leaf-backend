package com.team.leaf.shopping.wish.controller;

import com.team.leaf.common.custom.LogIn;
import com.team.leaf.shopping.wish.dto.WishRequest;
import com.team.leaf.shopping.wish.dto.WishResponse;
import com.team.leaf.shopping.wish.service.WishService;
import com.team.leaf.user.account.entity.AccountDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @GetMapping("/product/wish")
    public ResponseEntity<List<WishResponse>> wishPage(@LogIn AccountDetail accountDetail){
        return ResponseEntity.ok(wishService.findWishById(accountDetail));
    }

    @PostMapping("/product/wish")
    public ResponseEntity<Boolean> addWish(@RequestBody WishRequest wishRequest){
        return ResponseEntity.ok(wishService.addWish(wishRequest));
    }

    @DeleteMapping("/product/wish")
    public ResponseEntity<Boolean> deleteWish(@RequestBody WishRequest wishRequest){
        return ResponseEntity.ok(wishService.deleteById(wishRequest));
    }
}
