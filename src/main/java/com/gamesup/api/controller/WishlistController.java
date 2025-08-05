package com.gamesup.api.controller;

import com.gamesup.api.dto.wishlist.CreateWishlistDto;
import com.gamesup.api.dto.wishlist.WishlistDto;
import com.gamesup.api.response.ApiResponse;
import com.gamesup.api.service.WishlistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistDto>>> getWishlist(@RequestParam Long userId) {
        List<WishlistDto> wishlist = wishlistService.getWishlist(userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Wishlist fetched successfully", wishlist));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WishlistDto>> addToWishlist(@RequestBody @Valid CreateWishlistDto dto) {
        WishlistDto created = wishlistService.addToWishlist(dto.userId(), dto.gameId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Game added to wishlist successfully", created));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> removeFromWishlist(@RequestParam Long userId, @RequestParam Long gameId) {
        wishlistService.removeFromWishlist(userId, gameId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Game removed from wishlist successfully"));
    }
}
