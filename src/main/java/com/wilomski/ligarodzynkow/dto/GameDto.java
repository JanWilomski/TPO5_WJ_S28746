package com.wilomski.ligarodzynkow.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameDto(UUID id, int teamAScore, int teamBScore, LocalDateTime playedAt, List<PlayerDto> teamA, List<PlayerDto> teamB) {
}
