package com.gamesup.api.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryDto(@NotBlank String name) {}
