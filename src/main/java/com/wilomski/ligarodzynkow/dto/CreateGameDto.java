package com.wilomski.ligarodzynkow.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateGameDto(
        @Min(0) int teamAScore,
        @Min(0) int teamBScore,
        @NotEmpty List<UUID> teamA,
        @NotEmpty List<UUID> teamB
) {}
