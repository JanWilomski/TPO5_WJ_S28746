package com.wilomski.ligarodzynkow.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        // 1. Tworzymy komponent nagłówka aplikacji (tekst H1)
        H1 logo = new H1("Liga Rodzynków");

        // Czysto estetyczne zmniejszenie domyślnego ogromnego fontu H1 za pomocą stylów CSS w Javie
        logo.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        // 2. Układamy elementy poziomo: przycisk schowania menu (DrawerToggle) oraz nasze logo
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        // Środkujemy elementy w pionie wewnątrz paska górnego
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.getStyle().set("padding", "0 1em");

        // 3. Dodajemy stworzony pasek do wbudowanego obszaru Navbar w AppLayout
        addToNavbar(header);
    }

    private void createDrawer() {
        // 1. Tworzymy odnośniki nawigacyjne, wiążąc je z klasami konkretnych widoków
        RouterLink rankingLink = new RouterLink("Ranking", RankingView.class);
        RouterLink playersLink = new RouterLink("Gracze", PlayersView.class);
        RouterLink historyLink = new RouterLink("Historia", HistoryView.class);

        // 2. Układamy linki pionowo, jeden pod drugim
        VerticalLayout sidebar = new VerticalLayout(rankingLink, playersLink, historyLink);

        // Dodajemy lekkie wcięcie, żeby linki nie dotykały samej krawędzi ekranu
        sidebar.getStyle().set("padding", "1em");

        // 3. Dodajemy stworzony pionowy układ do bocznej szuflady (Drawer)
        addToDrawer(sidebar);
    }
}