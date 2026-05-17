package com.wilomski.ligarodzynkow.repository;

import com.wilomski.ligarodzynkow.entity.GamePlayer;
import com.wilomski.ligarodzynkow.entity.GamePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, GamePlayerId>{
    boolean existsByPlayerId(UUID playerId);
}
