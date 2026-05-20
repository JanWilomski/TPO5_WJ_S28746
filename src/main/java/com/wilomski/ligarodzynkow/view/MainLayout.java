package com.wilomski.ligarodzynkow.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle("Liga Rodzynków")
public class MainLayout extends AppLayout {

    private final Tabs menu;

    public MainLayout() {
        H1 title = new H1("Liga rodzynków");
        title.getStyle().set("text-align", "center");
        title.getStyle().setFontWeight(Style.FontWeight.BOLD);
        menu = createMenu();
        VerticalLayout header = new VerticalLayout(title, menu);
        header.setWidthFull();
        header.setPadding(false);
        header.setSpacing(false);


        addToNavbar(header);
    }

    private Tabs createMenu() {
        Tabs tabs = new Tabs();
        tabs.add(
                createTab("Ranking", RankingView.class),
                createTab("History", HistoryView.class),
                createTab("Players", PlayersView.class)
        );
        return tabs;
    }

    private Component createTab(String text, Class<? extends Component> componentClass) {
        Tab tab = new Tab();
        RouterLink link = new RouterLink(text, componentClass);
        tab.add(link);
        return tab;
    }

}