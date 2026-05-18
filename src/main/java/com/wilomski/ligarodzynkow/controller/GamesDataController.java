package com.wilomski.ligarodzynkow.controller;

import com.wilomski.ligarodzynkow.dto.CreateGameDto;
import com.wilomski.ligarodzynkow.dto.GameDto;
import com.wilomski.ligarodzynkow.dto.PlayerDto;
import com.wilomski.ligarodzynkow.entity.Game;
import com.wilomski.ligarodzynkow.entity.Team;
import com.wilomski.ligarodzynkow.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/data/games")
@RequiredArgsConstructor
public class GamesDataController {
    private final GameService gameService;

    @GetMapping
    public List<GameDto> getAllGames(){
        List<Game> games = gameService.findRecent(100);
        List<GameDto> gameDtos = new ArrayList<>();
        for(Game game : games){
            List<PlayerDto> teamA = game.getGamePlayers().stream()
                    .filter(gp -> gp.getTeam() == Team.A)
                    .map(gp -> new PlayerDto(gp.getPlayer().getId(), gp.getPlayer().getName()))
                    .toList();

            List<PlayerDto> teamB = game.getGamePlayers().stream()
                    .filter(gp -> gp.getTeam() == Team.B)
                    .map(gp -> new PlayerDto(gp.getPlayer().getId(), gp.getPlayer().getName()))
                    .toList();
            gameDtos.add(new GameDto(game.getId(), game.getTeamAScore(), game.getTeamBScore(), game.getPlayedAt(), teamA, teamB));

        }

        return gameDtos;
    }

    @PostMapping
    public GameDto createGame(@RequestBody @Valid CreateGameDto dto){
        Game game = gameService.create(dto.teamAScore(), dto.teamBScore(), dto.teamA(), dto.teamB());
        List<PlayerDto> teamA = game.getGamePlayers().stream()
                .filter(gp -> gp.getTeam() == Team.A)
                .map(gp -> new PlayerDto(gp.getPlayer().getId(), gp.getPlayer().getName()))
                .toList();

        List<PlayerDto> teamB = game.getGamePlayers().stream()
                .filter(gp -> gp.getTeam() == Team.B)
                .map(gp -> new PlayerDto(gp.getPlayer().getId(), gp.getPlayer().getName()))
                .toList();
        return new GameDto(game.getId(), game.getTeamAScore(), game.getTeamBScore(), game.getPlayedAt(), teamA, teamB);
    }
    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable UUID id){
        gameService.delete(id);
    }

}
