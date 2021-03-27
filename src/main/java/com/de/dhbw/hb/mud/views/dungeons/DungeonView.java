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

/**
 * The main view is a top-level placeholder for other views.
 */
@PageTitle("Dungeon")
public class DungeonView extends VerticalLayout {

    @Autowired
    private final DungeonRepository repoDungeon;

    @Autowired
    private final RoomRepository repoRoom;

    private final TextField nameField =new TextField("Name:");
    private final Button addRoomButton =new Button("Add a room");
    private final Dialog newRoomDialog = new Dialog();

    private final Button commitButton =new Button("Commit");

    private final TextField nameRoomField = new TextField("Name:");
    private final TextField selectRegionField = new TextField();
    private final NumberField xCoordinateField = new NumberField("x Coordinate");
    private final NumberField yCoordinateField = new NumberField("y Coordinate");
    private final Button commitRoomButton = new Button("Commit");

    private final ArrayList<Room> addedRooms = new ArrayList<>();

    public DungeonView(DungeonRepository repoDungeon, RoomRepository repoRoom) {
        this.repoDungeon = repoDungeon;
        this.repoRoom = repoRoom;

        newRoomDialog.add(
                new VerticalLayout(
                        new Text ("Create your Room!"),
                        nameRoomField,
                        selectRegionField,
                        xCoordinateField,
                        yCoordinateField,
                        new Div(
                                commitRoomButton
                        )
                )
        );

        add(new H1("Create your Dungeon"),
                nameField,
                addRoomButton,
                commitButton);

        addListeners();
    }

    private void addListeners() {
        addRoomButton.addClickListener(e-> newRoomDialog.open());

        commitRoomButton.addClickListener(e->{
            int[] coordinates = new int[2];
            coordinates[0] = xCoordinateField.getValue().intValue();
            coordinates[1] = yCoordinateField.getValue().intValue();

            Room r = new Room(nameRoomField.getValue(), selectRegionField.getValue(), coordinates);
            Notification.show("Added!");
            addedRooms.add(r);
            repoRoom.save(r);
            nameRoomField.clear();
            selectRegionField.clear();
            xCoordinateField.clear();
            yCoordinateField.clear();
        });

        commitButton.addClickListener(e->{
            Dungeon d = new Dungeon(nameField.getValue());
            repoDungeon.save(d);
            for (Room r : addedRooms) {
                r.setDungeonID(d.getId());
                repoRoom.save(r);
            }
            Notification.show("Dungeon created!");
            nameField.clear();
            addedRooms.clear();
        });
    }
}

