package com.de.dhbw.hb.mud.views.lobby;

import ch.qos.logback.core.status.Status;
import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import com.de.dhbw.hb.mud.service.registration.DungeonService;
import com.de.dhbw.hb.mud.views.main.MainView;
import com.sun.xml.bind.v2.model.core.ID;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.util.Hashtable;


//@Route(value = "lobby", layout = MainView.class)
@CssImport("./views/dungeon/lobby-view.css")
@PageTitle("Lobby")
public class Lobby extends VerticalLayout {

    Grid<Dungeon> dungeonList = new Grid<>(Dungeon.class);
    Button joinGame = new Button("Spiel beitreten");
    Button deleteDungeon = new Button("Dungeon löschen");

    Hashtable<Long, Dungeon> dungeonHash = new Hashtable();


    @Autowired
    private DungeonService dungeonService;

    public Lobby(DungeonService dungeonService) {
        this.dungeonService = dungeonService;

        addClassName("list-view-dungeons");
        setSizeFull();
        configureDungeonList();
        configureButtons();

        add(dungeonList);
        add(joinGame);
        add(deleteDungeon);

        updateList();
    }

    private void configureButtons() {
        //Object selected = ((SingleSelectionModel) dungeonList.getSelectionModel());
        joinGame.addClickListener(click -> {
            dungeonList.getSelectedItems().forEach(item -> {
                Notification.show("Enter " + String.valueOf(item.getName()));
            });
        });
        deleteDungeon.addClickListener((click -> {
            Notification.show("Delete " + dungeonList.getSelectedItems().toString());
        }));
    }

    private void updateList() {
        //myTest.setDataProvider(Dungeon::getName);
        //myTest.setItems(dungeonService.findAll());
        dungeonList.setItems(dungeonService.findAll());
        for (Dungeon myDungeon : dungeonService.findAll()) {
            dungeonHash.put(myDungeon.getId(), myDungeon);
        }
    }

    private void configureDungeonList() {
        dungeonList.addClassName("dungeon-grid");
        dungeonList.setSizeFull();
        dungeonList.setColumns("name", "startRoom", "creatorID");
    }

}
