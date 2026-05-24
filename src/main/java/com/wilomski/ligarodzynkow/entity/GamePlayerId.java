package com.wilomski.ligarodzynkow.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GamePlayerId implements Serializable {

    private UUID gameId;
    private UUID playerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamePlayerId gp)) return false;
        return Objects.equals(gameId, gp.gameId) && Objects.equals(playerId, gp.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}