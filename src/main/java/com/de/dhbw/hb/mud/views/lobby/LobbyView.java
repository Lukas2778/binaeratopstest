package com.de.dhbw.hb.mud.views.lobby;

import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import com.de.dhbw.hb.mud.repository.PlayerCharacterRepository;
import com.de.dhbw.hb.mud.repository.RaceRepository;
import com.de.dhbw.hb.mud.repository.RoleRepository;
import com.de.dhbw.hb.mud.service.registration.DungeonService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;


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
    @Autowired
    private PlayerCharacterRepository repoAvatar;
    @Autowired
    private RaceRepository repoRace;
    @Autowired
    private RoleRepository repoRole;

    public LobbyView(DungeonService dungeonService, PlayerCharacterRepository aRepoAvatar, RaceRepository aRaceRepo, RoleRepository aRoleRepo  ) {
        this.dungeonService = dungeonService;
        this.repoAvatar = aRepoAvatar;
        this.repoRace = aRaceRepo;
        this.repoRole= aRoleRepo;

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
                if(VaadinSession.getCurrent().getAttribute(UserDto.class).getId() != item.getCreatorID()) {
                    configureAvatar(item);
                }else{

                    UI.getCurrent().getPage().setLocation("game/" + item.getId());

                }
            });
        });
        deleteDungeon.addClickListener(click -> {
            dungeonList.getSelectedItems().forEach(item -> {
                if(confirmRemoveDungeon(item)) {

                    if( VaadinSession.getCurrent().getAttribute(UserDto.class).getId() == item.getCreatorID()){
                        dungeonService.delete(item);
                        Notification.show(String.valueOf(item.getName()) + " removed");
                        updateList();
                    }else{
                        Notification.show("Nur der Dungeonmaster kann seinen Dungeon löschen.");

                    }
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
        CreateAvatarDialog avatarDialog = new CreateAvatarDialog(selectedDungeon, repoAvatar, repoRace, repoRole);
        avatarDialog.open();

    }

    private Boolean confirmRemoveDungeon(Dungeon dungeon){
        Button confirmDeletion=new Button("Delete");
        Button cancelDeletion=new Button("Abbrechen");
        Text dungeonText=new Text("Möchten Sie den Dungeon " + dungeon.getName() + " wirklich löschen?");

        return true;
    }



}
