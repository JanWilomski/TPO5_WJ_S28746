package com.wilomski.ligarodzynkow.service;

import com.wilomski.ligarodzynkow.entity.Game;
import com.wilomski.ligarodzynkow.entity.GamePlayer;
import com.wilomski.ligarodzynkow.entity.Player;
import com.wilomski.ligarodzynkow.entity.Team;
import com.wilomski.ligarodzynkow.repository.GameRepository;
import com.wilomski.ligarodzynkow.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<Game> findRecent(int limit){
        return gameRepository.findAllByOrderByPlayedAtDesc()
                .stream()
                .limit(Math.min(limit, 100))
                .toList();
    }

    @Transactional
    public Game create(int teamAScore, int teamBScore, List<UUID> teamAPlayerIds, List<UUID> teamBPlayerIds){
        if(teamAScore < 0 || teamBScore < 0){
            throw new IllegalArgumentException("Team score cannot be below 0");
        }
        if(teamAScore == teamBScore){
            throw new IllegalArgumentException("Teams cannot have same score");
        }
        if(teamAPlayerIds.isEmpty() || teamBPlayerIds.isEmpty()){
            throw new IllegalArgumentException("Teams cannot be empty");
        }
        if (teamAPlayerIds.stream().distinct().count() != teamAPlayerIds.size()) {
            throw new IllegalArgumentException("Duplicates in team A");
        }
        if (teamBPlayerIds.stream().distinct().count() != teamBPlayerIds.size()) {
            throw new IllegalArgumentException("Duplicates in team B");
        }
        for (UUID id : teamAPlayerIds) {
            if (teamBPlayerIds.contains(id)) {
                throw new IllegalArgumentException("Player " + id + " cannot be in both teams");
            }
        }

        List<UUID> allIds = new ArrayList<>();
        allIds.addAll(teamAPlayerIds);
        allIds.addAll(teamBPlayerIds);

        List<Player> players = playerRepository.findAllById(allIds);

        if (players.size() != allIds.size()) {
            throw new IllegalArgumentException("One or more players don't exist");
        }

        Game game = new Game();
        game.setTeamAScore(teamAScore);
        game.setTeamBScore(teamBScore);
        game.setPlayedAt(LocalDateTime.now());
        game.setId(UUID.randomUUID());

        Map<UUID, Player> playerById = players.stream()
                .collect(Collectors.toMap(Player::getId, p -> p));

        for(UUID playerId : teamAPlayerIds){
            Player p = playerById.get(playerId);

            GamePlayer gp = new GamePlayer();
            gp.setGame(game);
            gp.setPlayer(p);
            gp.setTeam(Team.A);

            game.getGamePlayers().add(gp);
        }
        for(UUID playerId : teamBPlayerIds){
            Player p = playerById.get(playerId);

            GamePlayer gp = new GamePlayer();
            gp.setGame(game);
            gp.setPlayer(p);
            gp.setTeam(Team.B);

            game.getGamePlayers().add(gp);
        }

        return gameRepository.save(game);
    }


    @Transactional
    public void delete(UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        gameRepository.delete(game);
    }
}