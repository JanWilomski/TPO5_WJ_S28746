package com.wilomski.ligarodzynkow.view;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.wilomski.ligarodzynkow.dto.RankingDto;
import com.wilomski.ligarodzynkow.service.RankingService;

@PageTitle("Liga Rodzynków")
@Route(value = "ranking", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class RankingView extends VerticalLayout {

    private final Grid<RankingDto> grid = new Grid<>();
    private final RankingService rankingService;
    public RankingView(RankingService rankingService) {
        this.rankingService = rankingService;
        setPadding(true);

        add(new H1("Ranking"));
        Grid<RankingDto> grid = new Grid<>();
        grid.addColumn(RankingDto::rank).setHeader("Pozycja").setAutoWidth(true);
        grid.addColumn(RankingDto::playerName).setHeader("Gracz").setAutoWidth(true);
        grid.addColumn(
                g -> String.format("%.2f", g.winrate()*100)
        ).setHeader("Winrate %").setAutoWidth(true);
        grid.addColumn(RankingDto::gamesPlayed).setHeader("Mecze").setAutoWidth(true);
        grid.addColumn(RankingDto::gamesWon).setHeader("Wygrane").setAutoWidth(true);
        grid.addColumn(RankingDto::gamesLost).setHeader("Przegrane").setAutoWidth(true);

        grid.setItems(rankingService.getRanking());

        add(grid);
    }
}
