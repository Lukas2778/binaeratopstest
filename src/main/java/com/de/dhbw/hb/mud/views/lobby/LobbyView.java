package com.de.dhbw.hb.mud.views.lobby;

import ch.qos.logback.core.status.Status;
import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import com.de.dhbw.hb.mud.service.registration.DungeonService;
import com.de.dhbw.hb.mud.views.AvatarKonfigurator.AvatarErstellenView;
import com.de.dhbw.hb.mud.views.Konfigurator.AvatarKonfiguratorView;
import com.de.dhbw.hb.mud.views.main.MainView;
import com.sun.xml.bind.v2.model.core.ID;
import com.vaadin.flow.component.Text;
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
public class LobbyView extends VerticalLayout {

    Text dungeonLobbyInfo=new Text("Wählen Sie einen Dungeon aus und tauchen Sie ein in die grenzenlose Welt der MUDs.");
    Grid<Dungeon> dungeonList = new Grid<>(Dungeon.class);
    Button joinGame = new Button("Dungeon beitreten");
    Button deleteDungeon = new Button("Dungeon löschen");



    @Autowired
    private DungeonService dungeonService;
    private DungeonRepository dungeonRepository;

    public LobbyView(DungeonService dungeonService) {
        this.dungeonService = dungeonService;

        addClassName("list-view-dungeons");
        setSizeFull();
        configureDungeonList();
        configureButtons();

        add(dungeonLobbyInfo);
        add(dungeonList);
        add(joinGame);
        add(deleteDungeon);

        updateList();
    }

    private void configureButtons() {
        //Object selected = ((SingleSelectionModel) dungeonList.getSelectionModel());
        joinGame.addClickListener(click -> {
            dungeonList.getSelectedItems().forEach(item -> {
                Notification.show("Entering " + String.valueOf(item.getName()));
                configureAvatar(item);
            });
        });
        deleteDungeon.addClickListener(click -> {
            dungeonList.getSelectedItems().forEach(item -> {
                if(confirmRemoveDungeon(item)) {
                    dungeonService.delete(item);
                    Notification.show(String.valueOf(item.getName()) + " removed");
                    updateList();
                }
            });
        });
    }

    private void updateList() {
        //myTest.setDataProvider(Dungeon::getName);
        //myTest.setItems(dungeonService.findAll());
        dungeonList.setItems(dungeonService.findAll());
    }

    private void configureDungeonList() {
        dungeonList.addClassName("dungeon-grid");
        dungeonList.setSizeFull();
        dungeonList.setColumns("name", "startRoom", "creatorID");
    }

    private void configureAvatar(Dungeon selectedDungeon) {
        AvatarErstellenView avatarDialog = new AvatarErstellenView(selectedDungeon);
        avatarDialog.open();
    }

    private Boolean confirmRemoveDungeon(Dungeon dungeon){
        Button confirmDeletion=new Button("Delete");
        Button cancelDeletion=new Button("Abbrechen");
        Text dungeonText=new Text("Möchten Sie den Dungeon " + dungeon.getName() + " wirklich löschen?");

        return true;
    }

}
