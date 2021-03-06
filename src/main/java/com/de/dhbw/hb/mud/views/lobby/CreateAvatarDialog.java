package com.de.dhbw.hb.mud.views.lobby;

import com.de.dhbw.hb.mud.model.Avatar.Avatar;
import com.de.dhbw.hb.mud.model.Avatar.Gender;
import com.de.dhbw.hb.mud.model.Race;
import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.model.Role;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.repository.PlayerCharacterRepository;
import com.de.dhbw.hb.mud.repository.RaceRepository;
import com.de.dhbw.hb.mud.repository.RoleRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


//@Route(value = "Konfigurator", layout = MainView.class)
@PageTitle("Avatar erstellen")
public class CreateAvatarDialog extends Dialog {

    @Autowired
    private PlayerCharacterRepository repoAvatar;
    @Autowired
    private RaceRepository repoRace;
    @Autowired
    private RoleRepository repoRole;


    HorizontalLayout layout = new HorizontalLayout();
    VerticalLayout formular = new VerticalLayout();
    VerticalLayout newAvatar = new VerticalLayout();

    private TextField name=new TextField("Name");
    private ComboBox<Race> raceCombobox = new ComboBox<>("Rasse");
    private ComboBox<Role> roleCombobox = new ComboBox<>("Rolle");
    private ComboBox<Gender> genderCombobox = new ComboBox<>("Geschlecht");
    private Button commit =new Button("Avatar erstellen");

    public CreateAvatarDialog(Dungeon dungeon, PlayerCharacterRepository aRepoAvatar, RaceRepository aRepoRace, RoleRepository aRepoRole){
        this.repoAvatar = aRepoAvatar;
        this.repoRace = aRepoRace;
        this.repoRole= aRepoRole;


        raceCombobox.setItemLabelGenerator(Race::getName);
        roleCombobox.setItemLabelGenerator(Role::getName);



        layout.getStyle().set("border", "1px solid #9E9E9E");
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        formular.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        initAvatar();

        commit.addClickListener(e->{
            Avatar newAvatar = new Avatar(
                    VaadinSession.getCurrent().getAttribute(UserDto.class).getId(),
                    dungeon.getId(),
                    name.getValue(),
                    raceCombobox.getValue(),
                    roleCombobox.getValue(),
                    genderCombobox.getValue());

            aRepoAvatar.save(newAvatar);
            startGame(dungeon.getId());
        });
        initFormula(dungeon);

        add(new H2("Erstelle deinen Avatar"),
                formular);
    }

    private void initFormula(Dungeon ADungeon){

        raceCombobox.setItems(findRaceInDungeon(ADungeon.getId()));
        raceCombobox.setPlaceholder("Rasse");

        roleCombobox.setItems(findRoleInDungeon(ADungeon.getId()));
        roleCombobox.setPlaceholder("Rolle");

        genderCombobox.setItems(Gender.values());
        genderCombobox.setPlaceholder("Geschlecht");

        formular.add(name,
                raceCombobox,
                roleCombobox,
                genderCombobox,
                commit);
    }

    private void initAvatar(){
//        newAvatar.add(newAvatarName,
//                newAvatarRace,
//                newAvatarRole,
//                newAvatarGender);
    }

    public ArrayList<Race> findRaceInDungeon(long dungeonId) {

        ArrayList<Race> result = new ArrayList<>();

        for (Race myRace : repoRace.findAll()) {
            if (dungeonId == myRace.getDungeonID()){
                result.add(myRace);
            }
        }
        return result;
    }
    public ArrayList<Role> findRoleInDungeon(long dungeonId) {

        ArrayList<Role> result = new ArrayList<>();

        for (Role myRole : repoRole.findAll()) {
            if (dungeonId == myRole.getDungeonID()){
                result.add(myRole);
            }
        }
        return result;
    }

    private void startGame(long dungeonID){
        UI.getCurrent().getPage().setLocation("game/" + dungeonID);
        //UI.getCurrent().getPage().setLocation("game");
    }
}
