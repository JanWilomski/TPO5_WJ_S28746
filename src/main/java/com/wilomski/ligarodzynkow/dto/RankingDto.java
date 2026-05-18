package com.wilomski.ligarodzynkow.dto;

import java.util.UUID;

public record RankingDto (UUID playerId, String playerName, int gamesPlayed, int gamesWon, int gamesLost, double winrate, int rank){}