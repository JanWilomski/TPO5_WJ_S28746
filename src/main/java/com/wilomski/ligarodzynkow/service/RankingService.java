package com.wilomski.ligarodzynkow.service;

import com.wilomski.ligarodzynkow.dto.RankingDto;
import com.wilomski.ligarodzynkow.entity.GamePlayer;
import com.wilomski.ligarodzynkow.entity.Player;
import com.wilomski.ligarodzynkow.entity.Team;
import com.wilomski.ligarodzynkow.repository.GamePlayerRepository;
import com.wilomski.ligarodzynkow.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final PlayerRepository playerRepository;
    private final GamePlayerRepository gamePlayerRepository;

    @Transactional(readOnly = true)
    public List<RankingDto> getRanking(){
        List<RankingDto> ranking = new ArrayList<>();
        List<Player> players = playerRepository.findAll();
        List<GamePlayer> gamePlayers = gamePlayerRepository.findAllWithGames();
        Map<UUID, List<GamePlayer>> byPlayer = gamePlayers.stream()
                .collect(Collectors.groupingBy(gp -> gp.getPlayer().getId()));
        for(Player p : players){
            List<GamePlayer> myGames = byPlayer.getOrDefault(p.getId(), List.of());
            int wins = 0;
            int losses = 0;

            for (GamePlayer gp : myGames) {

                if (!gp.getPlayer().getId().equals(p.getId())) {
                    continue;
                }
                boolean teamAWon = gp.getGame().getTeamAScore() > gp.getGame().getTeamBScore();
                boolean teamBWon = gp.getGame().getTeamBScore() > gp.getGame().getTeamAScore();

                if ((gp.getTeam() == Team.A && teamAWon) || (gp.getTeam() == Team.B && teamBWon)) {
                    wins++;
                } else if ((gp.getTeam() == Team.A && !teamAWon) || (gp.getTeam() == Team.B && !teamBWon)) {
                    losses++;
                }
            }
            int games = wins + losses;
            ranking.add(new RankingDto(p.getId(), p.getName(), games, wins, losses, games > 0 ? (double)wins/(double)games : 0, 0));
        }
        ranking.sort(
                Comparator.comparing(RankingDto::winrate, Comparator.reverseOrder())
                        .thenComparing(RankingDto::gamesWon, Comparator.reverseOrder())
                        .thenComparing(RankingDto::playerName)
        );
        List<RankingDto> sorted = new ArrayList<>();
        for(int i = 0; i < ranking.size(); i++){
            RankingDto r = ranking.get(i);
            sorted.add(new RankingDto(r.playerId(), r.playerName(), r.gamesPlayed(), r.gamesWon(), r.gamesLost(), r.winrate(), i+1));
        }
        return sorted;
    }
}
