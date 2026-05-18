package com.wilomski.ligarodzynkow.view;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class) // value = "" oznacza stronę główną
public class RankingView extends VerticalLayout { // zmieniliśmy Div na VerticalLayout dla lepszego układu
    public RankingView() {
        add(new H1("Tutaj będzie tabela ligowa"));
    }
}
