package com.de.dhbw.hb.mud.views.AvatarKonfigurator;

import com.de.dhbw.hb.mud.model.Avatar.Avatar;
import com.de.dhbw.hb.mud.model.Avatar.Gender;
import com.de.dhbw.hb.mud.model.Avatar.Race;
import com.de.dhbw.hb.mud.model.Avatar.Role;
import com.de.dhbw.hb.mud.repository.PlayerCharacterRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Avatar Konfigurieren")
public class AvatarKonfiguratorView extends VerticalLayout {

    @Autowired
    private PlayerCharacterRepository repo;

    HorizontalLayout layout = new HorizontalLayout();
    VerticalLayout formular = new VerticalLayout();
    VerticalLayout newAvatar = new VerticalLayout();

    private TextField name=new TextField("Name");
    private ComboBox<Race> raceCombobox = new ComboBox<>("Rasse");
    private ComboBox<Role> roleCombobox = new ComboBox<>("Rolle");
    private ComboBox<Gender> genderCombobox = new ComboBox<>("Geschlecht");
    private Button commit =new Button("Charakter erstellen");

    private TextField newAvatarName = new TextField("Name");
    private TextField newAvatarRace = new TextField("Rasse");
    private TextField newAvatarRole = new TextField("Rolle");
    private TextField newAvatarGender = new TextField("Geschlecht");

    public AvatarKonfiguratorView(PlayerCharacterRepository repo){
        this.repo = repo;
        layout.getStyle().set("border", "1px solid #9E9E9E");
        initAvatar();
        commit.addClickListener(e->{
            Avatar newAvatar = new Avatar(name.getValue(),raceCombobox.getValue(),roleCombobox.getValue(),genderCombobox.getValue());

            newAvatarName.setValue(newAvatar.getName());
            newAvatarRace.setValue(newAvatar.getRace().name());
            newAvatarRole.setValue(newAvatar.getRole().name());
            newAvatarGender.setValue(newAvatar.getGender().name());

            repo.save(newAvatar);
            name.clear();
            raceCombobox.clear();
            roleCombobox.clear();
            genderCombobox.clear();
            name.focus();
        });
        initFormula();
        layout.add(formular,
                newAvatar);
        add(new H1("Konfiguriere deinen Avatar"),
                layout);
    }

    private void initFormula(){
        raceCombobox.setItems(Race.values());
        raceCombobox.setPlaceholder("Rasse");

        roleCombobox.setItems(Role.values());
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
        newAvatar.add(newAvatarName,
                newAvatarRace,
                newAvatarRole,
                newAvatarGender);
    }

}
