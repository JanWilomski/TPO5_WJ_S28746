package com.wilomski.ligarodzynkow.service;

import com.wilomski.ligarodzynkow.entity.Player;
import com.wilomski.ligarodzynkow.repository.GamePlayerRepository;
import com.wilomski.ligarodzynkow.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GamePlayerRepository gamePlayerRepository;

    @Transactional(readOnly = true)
    public List<Player> findAll() {
        return playerRepository.findAllByOrderByNameAsc();
    }

    @Transactional
    public Player create(String name) {
        String trimmedName = name.trim();
        if (playerRepository.existsByNameIgnoreCase(trimmedName)) {
            throw new IllegalArgumentException("Player with name " + trimmedName + " already exists");
        }else if (trimmedName.length() <2 || trimmedName.length()>50) {
            throw new IllegalArgumentException("Player name must be between 2 and 50 characters");
        }
        Player player = new Player();
        player.setName(trimmedName);
        return playerRepository.save(player);
    }

    @Transactional
    public void delete(UUID playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        if (gamePlayerRepository.existsByPlayerId(playerId)) {
            throw new IllegalArgumentException("Can't delete player with games");
        }
        playerRepository.delete(player);
    }
}
