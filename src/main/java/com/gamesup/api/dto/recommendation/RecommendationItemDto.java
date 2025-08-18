package com.gamesup.api.dto.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecommendationItemDto(
        @JsonProperty("game_id") Long gameId,
        Double score,
        @JsonProperty("game_name") String gameName
) {}
