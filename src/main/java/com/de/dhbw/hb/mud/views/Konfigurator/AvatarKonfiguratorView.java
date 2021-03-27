package com.de.dhbw.hb.mud.views.Konfigurator;

import com.de.dhbw.hb.mud.model.Avatar.AvatarConfigurations;
import com.de.dhbw.hb.mud.model.Avatar.AvatarKonfiguratorVM;
import com.de.dhbw.hb.mud.repository.PlayerCharacterRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


//@Route(value = "Konfigurator", layout = MainView.class)
@PageTitle("Avatar Konfigurieren")
public class AvatarKonfiguratorView extends VerticalLayout {

    @Autowired
    private PlayerCharacterRepository repo;

    private AvatarKonfiguratorVM avatarConfigurations = new AvatarKonfiguratorVM();

    VerticalLayout createRoleLayout = new VerticalLayout();
    VerticalLayout createRaceLayout = new VerticalLayout();

    private TextField addRoleTF = new TextField();
    private Button addRoleButton =new Button("Rolle hinzufügen");
    private Text roles = new Text("Rollen:");

    private TextField addRaceTF = new TextField();
    private Button addRaceButton =new Button("Rolle hinzufügen");
    private Text races = new Text("Rassen:");

    private Button finishConfigButton =new Button("Konfiguration abschließen");

    private ArrayList<String> roleList = new ArrayList<String>();
    private ArrayList<String> raceList = new ArrayList<String>();


    public AvatarKonfiguratorView(PlayerCharacterRepository repo){
        this.repo = repo;

        initAddRoleLayout();
        initAddRaceLayout();

        addRoleButton.addClickListener(e->{
            String roleName = addRoleTF.getValue();
            if(roles.getText() == "Rollen:"){
                roles.setText(roles.getText() + roleName);
                roleList.add(roleName);
            }
            else if(roleName.isEmpty()){

            }
            else{
                roles.setText(roles.getText()+", " + roleName);
                roleList.add(roleName);
            }
            addRoleTF.clear();
        });

        addRaceButton.addClickListener(e->{
            String raceName = addRaceTF.getValue();
            if(races.getText() == "Rassen:"){
                races.setText(races.getText() + raceName);
                roleList.add(raceName);
            }
            else if(raceName.isEmpty()){

            }
            else{
                races.setText(races.getText()+", " + raceName);
                raceList.add(raceName);
            }
            addRaceTF.clear();
        });

        finishConfigButton.addClickListener(e->{
            avatarConfigurations.avatarConfigurations = new AvatarConfigurations(roleList, raceList);
            races.setText("");
        });

        add(createRoleLayout,
                createRaceLayout,
                finishConfigButton);
    }

    private void initAddRoleLayout(){
        HorizontalLayout addRoleLayout = new HorizontalLayout(
                addRoleTF,
                addRoleButton
        );

         createRoleLayout.add(
                new H1("Rollen"),
                roles,
                addRoleLayout
        );
    }

    private void initAddRaceLayout(){
        HorizontalLayout addRaceLayout = new HorizontalLayout(
                addRaceTF,
                addRaceButton
        );

        createRaceLayout.add(
                new H1("Rassen"),
                races,
                addRaceLayout
        );
    }

}
