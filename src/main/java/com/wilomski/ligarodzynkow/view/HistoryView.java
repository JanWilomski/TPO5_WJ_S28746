package com.wilomski.ligarodzynkow.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.wilomski.ligarodzynkow.dto.GameDto;
import com.wilomski.ligarodzynkow.dto.PlayerDto;
import com.wilomski.ligarodzynkow.entity.Team;
import com.wilomski.ligarodzynkow.service.GameService;
import com.wilomski.ligarodzynkow.service.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Route(value = "history", layout = MainLayout.class)
public class HistoryView extends VerticalLayout{
    private final Grid<GameDto> grid;
    private final GameService gameService;
    private final PlayerService playerService;


    public HistoryView(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.grid = new Grid<>(GameDto.class, false);
        H1 title = new H1("Historia gier");
        setPadding(true);

        Button addBtn = new Button("Dodaj grę", e -> openAddGameDialog());

        grid.addColumn(GameDto::playedAt).setHeader("Data");
        grid.addColumn(g -> g.teamA().stream()
                .map(PlayerDto::name)
                .collect(Collectors.joining(", "))).setHeader("Team A");
        grid.addColumn(GameDto::teamAScore).setHeader("Team A score");
        grid.addColumn(GameDto::teamBScore).setHeader("Team B score");
        grid.addColumn(g -> g.teamB().stream()
                .map(PlayerDto::name)
                .collect(Collectors.joining(", "))).setHeader("Team B");

        grid.addComponentColumn(game -> {
            Button editBtn = new Button("Edytuj");
            editBtn.addClickListener(e -> openEditDialog(game));
            return editBtn;
        }).setHeader("Edytuj").setWidth("100px").setFlexGrow(0);


        grid.addComponentColumn(game -> {
            Button deleteBtn = new Button("Usuń");
            deleteBtn.addClickListener(e -> deleteGame(game));
            return deleteBtn;
        }).setHeader("Usuń").setWidth("100px").setFlexGrow(0);


        add(title, addBtn, grid);
        refreshGrid();
    }

    private void openEditDialog(GameDto game) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edytuj grę");

        NumberField scoreA = new NumberField("Wynik Team A", String.valueOf(game.teamAScore()));
        NumberField scoreB = new NumberField("Wynik Team B", String.valueOf(game.teamBScore()));

        Button saveBtn = new Button("Zapisz grę");
        saveBtn.addClickListener(btn -> {
            try{
                gameService.updateScores(
                        game.id(),
                        scoreA.getValue().intValue(),
                        scoreB.getValue().intValue()
                );
                dialog.close();
                refreshGrid();
            } catch (Exception e){
                Notification.show(e.getMessage());
            }
        });

        dialog.add(scoreA, scoreB, saveBtn);
        dialog.open();
    }

    private void deleteGame(GameDto game) {
        gameService.delete(game.id());
        refreshGrid();
    }


    private void refreshGrid() {
        List<GameDto> games = gameService.findRecent(100);
        grid.setItems(games);
    }

    private void openAddGameDialog(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Dodaj grę");
        List<PlayerDto> teamA = new ArrayList<>();
        List<PlayerDto> teamB = new ArrayList<>();

        Grid<PlayerDto> playersGrid = new Grid<>(PlayerDto.class, false);
        playersGrid.addColumn(PlayerDto::name).setHeader("Zawodnik");

        playersGrid.addComponentColumn(p -> {
            Checkbox checkbox = new Checkbox();
            checkbox.addValueChangeListener(e -> {
                if (e.getValue()) {
                    teamA.add(p);
                } else {
                    teamA
                            .remove(p);
                }
            });
            return checkbox;
        }).setHeader("Team A");
        playersGrid.addComponentColumn(p -> {
            Checkbox checkbox = new Checkbox();
            checkbox.addValueChangeListener(e -> {
                if (e.getValue()) {
                    teamB.add(p);
                } else {
                    teamB.remove(p);
                }
            });
            return checkbox;
        }).setHeader("Team B");

        List<PlayerDto> players = playerService.findAll().stream()
                .map(p -> new PlayerDto(p.getId(), p.getName()))
                .toList();
        playersGrid.setItems(players);

        NumberField scoreA = new NumberField("Wynik Team A");
        NumberField scoreB = new NumberField("Wynik Team B");

        Button saveBtn = new Button("Zapisz grę");
        saveBtn.addClickListener(btn -> {
            try{
                gameService.create(
                        scoreA.getValue().intValue(),
                        scoreB.getValue().intValue(),
                        teamA.stream().map(PlayerDto::id).toList(),
                        teamB.stream().map(PlayerDto::id).toList()
                );
                dialog.close();
                refreshGrid();
            } catch (Exception e){
                Notification.show(e.getMessage());
            }
        });

        dialog.add(playersGrid, scoreA, scoreB, saveBtn);
        dialog.open();
    }

}
