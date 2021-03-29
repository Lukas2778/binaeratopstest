package com.de.dhbw.hb.mud.views.lobby;

import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.service.registration.DungeonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

//@Route(value = "lobby", layout = MainView.class)
@PageTitle("Lobby")
public class Lobby extends VerticalLayout {

    Grid<Dungeon> dungeonList = new Grid<>(Dungeon.class);
    Button joinGame = new Button("Spiel beitreten");

    @Autowired
    private DungeonService dungeonService;

    public Lobby(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
        addClassName("list-view-dungeons");
        setSizeFull();
        configureDungeonList();

        add(dungeonList);
        add(joinGame);

        updateList();
    }

    private void updateList() {
        dungeonList.setItems(dungeonService.findAll());
    }

    private void configureDungeonList() {
        dungeonList.addClassName("dungeon-grid");
        dungeonList.setSizeFull();
        dungeonList.setColumns("name", "startRoom", "creatorID");
    }
}

/*
public class AvailableGames extends HorizontalLayout {
    private Button JoinGame = new Button("Spiel beitreten");

    public AvailableGames() {
        add(JoinGame);
    }
}*/