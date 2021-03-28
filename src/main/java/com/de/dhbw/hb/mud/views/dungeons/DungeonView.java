package com.de.dhbw.hb.mud.views.dungeons;

import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.model.Room;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import com.de.dhbw.hb.mud.repository.RoomRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@PageTitle("Dungeon")
public class DungeonView extends VerticalLayout {

    @Autowired
    private final DungeonRepository repoDungeon;

    @Autowired
    private final RoomRepository repoRoom;

    private final TextField nameField =new TextField("Name:");
    private final Button addRoomButton =new Button("Erstelle einen Raum");
    private final Dialog newRoomDialog = new Dialog();

    private final Button commitButton =new Button("Hinzufügen");

    private final TextField nameRoomField = new TextField("Name:");
    private final TextField selectRegionField = new TextField("Region:");
    private final NumberField xCoordinateField = new NumberField("x Koordinate");
    private final NumberField yCoordinateField = new NumberField("y Koordinate");
    private final Button commitRoomButton = new Button("Hinzufügen");

    private final ArrayList<Room> addedRooms = new ArrayList<>();

    public DungeonView(DungeonRepository repoDungeon, RoomRepository repoRoom) {
        this.repoDungeon = repoDungeon;
        this.repoRoom = repoRoom;

        newRoomDialog.add(
                new VerticalLayout(
                        new Text ("Erstelle deinen Raum!"),
                        nameRoomField,
                        selectRegionField,
                        xCoordinateField,
                        yCoordinateField,
                        new Div(
                                commitRoomButton
                        )
                )
        );

        add(new H1("Erstelle deinen Dungeon"),
                nameField,
                addRoomButton,
                commitButton);

        addListeners();
        initializeSettings();
    }

    private void initializeSettings() {
        nameRoomField.setHelperText("z.B.: Wald der Finsternis");
        selectRegionField.setHelperText("z.B.: Wald");
    }

    private void addListeners() {
        addRoomButton.addClickListener(e-> newRoomDialog.open());

        commitRoomButton.addClickListener(e->{
            if (validateRoom()) {
                int[] coordinates = new int[2];
                coordinates[0] = xCoordinateField.getValue().intValue();
                coordinates[1] = yCoordinateField.getValue().intValue();

                Room r = new Room(nameRoomField.getValue(), selectRegionField.getValue(), coordinates);
                Notification.show("Raum wurde hinzugefügt");
                addedRooms.add(r);
                repoRoom.save(r);
                nameRoomField.clear();
                selectRegionField.clear();
                xCoordinateField.clear();
                yCoordinateField.clear();
            } else {
                Notification.show("Bitte füllen sie alle Felder aus");
            }
        });

        commitButton.addClickListener(e->{
            if (validateDungeon()) {
                Dungeon d = new Dungeon(nameField.getValue());
                repoDungeon.save(d);
                for (Room r : addedRooms) {
                    r.setDungeonID(d.getId());
                    repoRoom.save(r);
                }
                Notification.show("Der Dungeon wurde erstellt");
                nameField.clear();
                addedRooms.clear();
            } else {
                Notification.show("Bitte geben sie einen Namen ein");
            }
        });
    }

    private boolean validateDungeon() {
        if (nameField.isEmpty()) {
            nameField.focus();
            return false;
        }
        return true;
    }

    private boolean validateRoom() {
        if (nameRoomField.isEmpty()) {
            nameRoomField.focus();
            return false;
        }else if (selectRegionField.isEmpty()) {
            selectRegionField.focus();
            return false;
        }else if (xCoordinateField.isEmpty()) {
            xCoordinateField.focus();
            return false;
        }else if (yCoordinateField.isEmpty()) {
            yCoordinateField.focus();
            return false;
        }
        return true;
    }
}

