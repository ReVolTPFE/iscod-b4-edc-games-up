package com.gamesup.api.dto.publisher;

import jakarta.validation.constraints.NotBlank;

public record UpdatePublisherDto(@NotBlank String name) {}
