package com.de.dhbw.hb.mud.views.dungeons;

import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.model.Room;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import com.de.dhbw.hb.mud.repository.RoomRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

@PageTitle("Dungeon")
public class DungeonView extends VerticalLayout {

    @Autowired
    private final DungeonRepository repoDungeon;

    @Autowired
    private final RoomRepository repoRoom;

    private final TextField nameField =new TextField("Name:");

    private final OrderedList roomsList = new OrderedList();

    private Button addRoomButton = new Button("Füge einen Raum hinzu");

    private final Button commitButton =new Button("Hinzufügen");

    private final ArrayList<Room> addedRooms = new ArrayList<>();

    private HashMap<Integer, Room> layoutToRoom = new HashMap<>();

    private ArrayList<String> usedIDs = new ArrayList<>();

    public DungeonView(DungeonRepository repoDungeon, RoomRepository repoRoom) {
        this.repoDungeon = repoDungeon;
        this.repoRoom = repoRoom;

        roomsList.add(addRoomButton);

        add(new H1("Erstelle deinen Dungeon"),
                nameField,
                roomsList,
                commitButton);

        addListeners();
        initializeSettings();
    }

    private void initializeSettings() {

    }

    private String getNewID() {
        int i = 1;
        while (usedIDs.contains(String.valueOf(i))) {
            i++;
        }
        String s = String.valueOf(i);
        usedIDs.add(s);
        return s;
    }

    private void addRoomToLayout() {
        roomsList.remove(addRoomButton);

        TextField number = new TextField("ID");
        String iD = getNewID();
        number.setValue(iD);
        number.setReadOnly(true);
        number.setWidth("30px");
        TextField name = new TextField("Name:");
        ComboBox<String> north = new ComboBox<>("Norden");
        ComboBox<String> south = new ComboBox<>("Süden");
        ComboBox<String> west = new ComboBox<>("Westen");
        ComboBox<String> east = new ComboBox<>("Osten");

        north.setItems(usedIDs);
        south.setItems(usedIDs);
        west.setItems(usedIDs);
        east.setItems(usedIDs);

        HorizontalLayout horLay = new HorizontalLayout(
                number,
                name,
                north,
                south,
                west,
                east
        );

        roomsList.add(horLay);

        Room r = new Room();

        addedRooms.add(r);

        layoutToRoom.put(Integer.parseInt(iD), r);

        roomsList.add(addRoomButton);
    }

    private void createRoom(HorizontalLayout horLay) {
        Component[] components = horLay.getChildren().toArray(Component[]::new);

        Room r = layoutToRoom.get(Integer.parseInt(((TextField) components[0]).getValue()));
        r.setName(((TextField) components[1]).getValue());
        repoRoom.save(r);
    }

    private void createRooms() {
        roomsList.getChildren().forEach(component -> {
            if (component instanceof HorizontalLayout){
                createRoom((HorizontalLayout) component);
            }
        });
    }

    private void setNeighbours() {
        HorizontalLayout[] layouts = roomsList.getChildren().
                filter(component -> component instanceof HorizontalLayout)
                .toArray(HorizontalLayout[]::new);

        for (HorizontalLayout l1 : layouts) {
            Component[] components = l1.getChildren().toArray(Component[]::new);
            Room r = layoutToRoom.get(Integer.parseInt(((TextField) components[0]).getValue()));

            if (!((ComboBox<String>) components[2]).isEmpty()) {
                r.setNorthRoomID(Long.parseLong(((ComboBox<String>) components[2]).getValue()));
            }
            if (!((ComboBox<String>) components[3]).isEmpty()) {
                r.setSouthRoomID(Long.parseLong(((ComboBox<String>) components[3]).getValue()));
            }
            if (!((ComboBox<String>) components[4]).isEmpty()) {
                r.setWestRoomID(Long.parseLong(((ComboBox<String>) components[4]).getValue()));
            }
            if (!((ComboBox<String>) components[5]).isEmpty()) {
                r.setEastRoomID(Long.parseLong(((ComboBox<String>) components[5]).getValue()));
            }
            repoRoom.save(r);
        }
    }

    private void addListeners() {
        addRoomButton.addClickListener(e-> {
            addRoomToLayout();
        });

        commitButton.addClickListener(e->{
            if (validateDungeon()) {
                createRooms();
                setNeighbours();
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
        return true;
    }
}

