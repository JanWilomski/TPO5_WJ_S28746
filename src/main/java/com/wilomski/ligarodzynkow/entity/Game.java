package com.wilomski.ligarodzynkow.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Check;


@Entity
@Table(name = "games")
@Check(constraints = "team_a_score >= 0 AND team_b_score >= 0 AND team_a_score <> team_b_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "team_a_score", nullable = false)
    private int teamAScore;

    @Column(name = "team_b_score", nullable = false)
    private int teamBScore;

    @Column(name = "played_at")
    private LocalDateTime playedAt;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GamePlayer> gamePlayers = new ArrayList<>();
}