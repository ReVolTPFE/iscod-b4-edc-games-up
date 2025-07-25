package com.gamesup.api.dto.author;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorDto(@NotBlank String name) {}
