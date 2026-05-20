package com.wilomski.ligarodzynkow.controller;

import com.wilomski.ligarodzynkow.dto.CreatePlayerDto;
import com.wilomski.ligarodzynkow.dto.PlayerDto;
import com.wilomski.ligarodzynkow.dto.UpdatePlayerDto;
import com.wilomski.ligarodzynkow.entity.Player;
import com.wilomski.ligarodzynkow.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/data/players")
@RequiredArgsConstructor
public class PlayersDataController {
    private final PlayerService playerService;

    @GetMapping
    public List<PlayerDto> getAllPlayers(){
        return playerService.findAll().stream().map(player -> (new PlayerDto(player.getId(), player.getName()))).toList();
    }

    @PostMapping
    public PlayerDto createPlayer(@RequestBody @Valid CreatePlayerDto dto){
        Player savedPlayer = playerService.create(dto.name());
        return new PlayerDto(savedPlayer.getId(), savedPlayer.getName());
    }

    @DeleteMapping("/{id}")
    void deletePlayer(@PathVariable UUID id){
        playerService.delete(id);
    }

    @PutMapping("/{id}")
    public PlayerDto changePlayerName(@PathVariable UUID id, @RequestBody @Valid UpdatePlayerDto dto){
        PlayerDto updated = playerService.changePlayerName(id, dto.name());
        return new PlayerDto(updated.id(), updated.name());
    }
}
