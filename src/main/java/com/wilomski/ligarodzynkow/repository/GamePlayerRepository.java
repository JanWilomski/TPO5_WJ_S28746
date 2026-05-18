package com.wilomski.ligarodzynkow.repository;

import com.wilomski.ligarodzynkow.entity.GamePlayer;
import com.wilomski.ligarodzynkow.entity.GamePlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, GamePlayerId>{
    boolean existsByPlayerId(UUID playerId);

    @Query("SELECT gp FROM GamePlayer gp JOIN FETCH gp.game")
    List<GamePlayer> findAllWithGames();
}
