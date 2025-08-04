package com.gamesup.api.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryDto(@NotBlank String name) {}
