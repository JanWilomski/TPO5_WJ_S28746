package com.wilomski.ligarodzynkow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "game_players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GamePlayer {

    @EmbeddedId
    private GamePlayerId id = new GamePlayerId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Team team;
}