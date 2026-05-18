package com.wilomski.ligarodzynkow.controller;

import com.wilomski.ligarodzynkow.dto.RankingDto;
import com.wilomski.ligarodzynkow.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data/ranking")
@RequiredArgsConstructor
public class RankingDataController{
    private final RankingService rankingService;

    @GetMapping
    public List<RankingDto> getRanking(){
        return rankingService.getRanking();
    }

}
