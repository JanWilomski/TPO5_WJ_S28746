package com.wilomski.ligarodzynkow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePlayerDto(@NotBlank @Size(min = 2, max = 50) String name) {
}
