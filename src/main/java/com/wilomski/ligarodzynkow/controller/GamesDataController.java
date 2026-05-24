package com.wilomski.ligarodzynkow.controller;

import com.wilomski.ligarodzynkow.dto.CreateGameDto;
import com.wilomski.ligarodzynkow.dto.GameDto;
import com.wilomski.ligarodzynkow.dto.PlayerDto;
import com.wilomski.ligarodzynkow.dto.UpdateGameDto;
import com.wilomski.ligarodzynkow.entity.Game;
import com.wilomski.ligarodzynkow.entity.Team;
import com.wilomski.ligarodzynkow.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
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
    public List<GameDto> getAllGames(
            @SortDefault(sort = "playedAt", direction = Sort.Direction.DESC) Sort sort) {
        return gameService.findAll(sort);
    }

    @PostMapping
    public GameDto createGame(@RequestBody @Valid CreateGameDto dto){
        return gameService.create(dto.teamAScore(), dto.teamBScore(), dto.teamA(), dto.teamB());
    }
    @DeleteMapping("/{id}")
    void deleteGame(@PathVariable UUID id){
        gameService.delete(id);
    }

    @PutMapping("/{id}")
    public GameDto updateGameScore(@PathVariable UUID id, @RequestBody @Valid UpdateGameDto dto) {
        return gameService.updateScores(id, dto.teamAScore(), dto.teamBScore());
    }



}
