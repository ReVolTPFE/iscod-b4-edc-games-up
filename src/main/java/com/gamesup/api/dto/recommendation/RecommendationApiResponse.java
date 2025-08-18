package com.gamesup.api.dto.recommendation;

import java.util.List;

public record RecommendationApiResponse(List<RecommendationItemDto> recommendations) {}
