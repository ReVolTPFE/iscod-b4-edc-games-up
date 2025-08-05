package com.gamesup.api.dto.wishlist;

import jakarta.validation.constraints.NotNull;

public record CreateWishlistDto(@NotNull Long userId, @NotNull Long gameId) {}
