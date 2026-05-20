package com.wilomski.ligarodzynkow.view;

import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.wilomski.ligarodzynkow.dto.PlayerDto;
import com.wilomski.ligarodzynkow.service.PlayerService;

import java.util.List;

@Route(value = "players", layout = MainLayout.class)
public class PlayersView extends VerticalLayout {
    private final Grid<PlayerDto> grid;
    private final PlayerService playerService;



    public PlayersView(PlayerService playerService) {
        H1 title = new H1("Gracze");
        this.playerService = playerService;
        setPadding(true);
        Button addBtn = new Button("Dodaj gracza", e -> openAddDialog());

        grid = new Grid<>(PlayerDto.class, false);
        grid.addColumn(PlayerDto::name).setHeader("Imię");

        grid.addComponentColumn(player -> {
            Button deleteBtn = new Button("Usuń");
            deleteBtn.addClickListener(e -> deletePlayer(player));
            return deleteBtn;
        }).setHeader("Usuń").setWidth("100px").setFlexGrow(0);

        grid.addComponentColumn(player -> {
            Button editBtn = new Button("Edytuj");
            editBtn.addClickListener(e -> openEditDialog(player));
            return editBtn;
        }).setHeader("Edytuj").setWidth("100px").setFlexGrow(0);


        add(title, addBtn, grid);
        refreshGrid();
    }

    private void refreshGrid() {
        List<PlayerDto> players = playerService.findAll().stream()
                .map(p -> new PlayerDto(p.getId(), p.getName()))
                .toList();
        grid.setItems(players);
    }

    private void deletePlayer(PlayerDto player) {
        try {
            playerService.delete(player.id());
            Notification.show("Usunięto gracza");
            refreshGrid();
        } catch (Exception e){
            Notification.show(e.getMessage());
        }
    }

    private void openAddDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Dodaj zawodnika");

        TextField nameField = new TextField("Imię");

        dialog.add(nameField);

        Button saveBtn = new Button("Zapisz");
        saveBtn.addClickListener(btn -> {
            try{
                playerService.create(nameField.getValue());
                dialog.close();
                refreshGrid();
            } catch (Exception e){
                Notification.show(e.getMessage());
            }
        });

        dialog.add(saveBtn);

        dialog.open();
    }
    private void openEditDialog(PlayerDto player) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edytuj zawodnika");

        TextField nameField = new TextField("Imię", player.name());

        dialog.add(nameField);

        Button saveBtn = new Button("Zapisz");
        saveBtn.addClickListener(btn -> {
            try{
                playerService.changePlayerName(player.id(), nameField.getValue());
                dialog.close();
                refreshGrid();
            } catch (Exception e){
                Notification.show(e.getMessage());
            }
        });

        dialog.add(saveBtn);

        dialog.open();
    }
}
