package com.wilomski.ligarodzynkow.dto;

import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateGameDto(@Min(0) int teamAScore, @Min(0) int teamBScore) {
}
