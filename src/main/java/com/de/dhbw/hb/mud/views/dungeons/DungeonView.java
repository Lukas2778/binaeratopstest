package com.de.dhbw.hb.mud.views.dungeons;

import com.de.dhbw.hb.mud.model.*;
import com.de.dhbw.hb.mud.repository.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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

    @Autowired
    private final RaceRepository repoRace;

    @Autowired
    private final RoleRepository repoRole;

    @Autowired
    private final NPCRepository repoNPC;

    @Autowired
    private final ItemRepository repoItem;

    private final TextField nameField =new TextField("Name:");

    private final Button raceButton = new Button("Mögliche Rassen");

    private final Button roleButton = new Button("Mögliche Klassen");

    private final OrderedList roomsList = new OrderedList();

    private Button addRoomButton = new Button("Füge einen Raum hinzu");

    private final Button commitButton =new Button("Hinzufügen");

    private final Dialog raceDialog = new Dialog();

    private final Dialog roleDialog = new Dialog();

    private final ArrayList<Room> addedRooms = new ArrayList<>();

    private HashMap<Integer, Room> layoutToRoom = new HashMap<>();

    private HashMap<String, Dialog> idToNPCDialog = new HashMap<>();

    private HashMap<String, Dialog> idToItemDialog = new HashMap<>();

    private ArrayList<String> usedIDs = new ArrayList<>();

    public DungeonView(DungeonRepository repoDungeon, RoomRepository repoRoom, RoleRepository repoRole,
                       RaceRepository repoRace, NPCRepository repoNPC, ItemRepository repoItem) {
        this.repoDungeon = repoDungeon;
        this.repoRoom = repoRoom;
        this.repoRole = repoRole;
        this.repoRace = repoRace;
        this.repoNPC = repoNPC;
        this.repoItem = repoItem;

        roomsList.add(addRoomButton);

        Button addRaceButton = new Button("Rasse hinzufügen");
        addRaceButton.addClickListener(event -> {
            raceDialog.add(new HorizontalLayout(new TextField("Rasse:")));
        });

        Button addRoleButton = new Button("Rolle hinzufügen");
        addRoleButton.addClickListener(event -> {
            roleDialog.add(new HorizontalLayout(new TextField("Rolle:")));
        });

        raceDialog.add(addRaceButton);
        roleDialog.add(addRoleButton);

        addListeners();
        initializeSettings();

        add(new H1("Erstelle deinen Dungeon"),
                nameField,
                new HorizontalLayout(raceButton, roleButton),
                roomsList,
                commitButton);
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

        Dialog npcDialog = new Dialog();
        Dialog itemDialog = new Dialog();

        idToNPCDialog.put(iD, npcDialog);
        idToItemDialog.put(iD, itemDialog);

        Button npcAddButton = new Button("Hinzufügen", e-> {
            npcDialog.add(new HorizontalLayout(
                            new TextField("Name:"),
                            new TextField("Rasse:"),
                            new TextArea("Beschreibung:")
                    ));
        });
        Button itemAddButton = new Button("Hinzufügen", e-> {
            itemDialog.add(new HorizontalLayout(
                    new TextField("Name:"),
                    new Checkbox("Konsumierbar:"),
                    new TextArea("Beschreibung:")
            ));
        });

        npcDialog.add(npcAddButton);
        itemDialog.add(itemAddButton);

        Button npcButton = new Button("NPCs", e -> npcDialog.open());
        Button itemButton = new Button("Items", e -> itemDialog.open());

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
                east,
                npcButton,
                itemButton
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
                Long id = layoutToRoom.get(Integer.parseInt(((ComboBox<String>) components[2]).getValue())).getId();
                r.setNorthRoomID(id);
            }
            if (!((ComboBox<String>) components[3]).isEmpty()) {
                Long id = layoutToRoom.get(Integer.parseInt(((ComboBox<String>) components[3]).getValue())).getId();
                r.setSouthRoomID(id);
            }
            if (!((ComboBox<String>) components[4]).isEmpty()) {
                Long id = layoutToRoom.get(Integer.parseInt(((ComboBox<String>) components[4]).getValue())).getId();
                r.setWestRoomID(id);
            }
            if (!((ComboBox<String>) components[5]).isEmpty()) {
                Long id = layoutToRoom.get(Integer.parseInt(((ComboBox<String>) components[5]).getValue())).getId();
                r.setEastRoomID(id);
            }
            saveNPCs(idToNPCDialog.get(((TextField) components[0]).getValue()), r.getId());
            saveItems(idToItemDialog.get(((TextField) components[0]).getValue()), r.getId());
            repoRoom.save(r);
        }
    }

    private void saveNPCs(Dialog d, Long roomID) {
        HorizontalLayout[] layouts = d.getChildren()
                .filter(component -> component instanceof HorizontalLayout)
                .toArray(HorizontalLayout[]::new);

        for (HorizontalLayout layout : layouts) {
            Component[] components = layout.getChildren().toArray(Component[]::new);
            NPC npc = new NPC();
            npc.setName(((TextField) components[0]).getValue());
            npc.setRace(((TextField) components[1]).getValue());
            npc.setDescription(((TextArea) components[2]).getValue());
            npc.setRoomID(roomID);
            repoNPC.save(npc);
        }
    }

    private void saveItems(Dialog d, Long roomID) {
        HorizontalLayout[] layouts = d.getChildren()
                .filter(component -> component instanceof HorizontalLayout)
                .toArray(HorizontalLayout[]::new);

        for (HorizontalLayout layout : layouts) {
            Component[] components = layout.getChildren().toArray(Component[]::new);
            Item item = new Item();
            item.setName(((TextField) components[0]).getValue());
            item.setConsumable(((Checkbox) components[1]).getValue());
            item.setDescription(((TextArea) components[2]).getValue());
            item.setRoomID(roomID);
            repoItem.save(item);
        }
    }

    private void saveRoles(Long dungeonID) {
        HorizontalLayout[] layouts = roleDialog.getChildren().
                filter(component -> component instanceof HorizontalLayout)
                .toArray(HorizontalLayout[]::new);
        for (HorizontalLayout layout : layouts) {
            TextField[] fields = layout.getChildren().
                    filter(component -> component instanceof TextField)
                    .toArray(TextField[]::new);
            Role r = new Role();
            r.setName(fields[0].getValue());
            r.setDungeonID(dungeonID);
            repoRole.save(r);
        }
    }

    private void saveRaces(Long dungeonID) {
        HorizontalLayout[] layouts = raceDialog.getChildren()
                .filter(component -> component instanceof HorizontalLayout)
                .toArray(HorizontalLayout[]::new);
        for (HorizontalLayout layout : layouts) {
            TextField[] fields = layout.getChildren()
                    .filter(component -> component instanceof TextField)
                    .toArray(TextField[]::new);
            Race r = new Race();
            r.setName(fields[0].getValue());
            r.setDungeonID(dungeonID);
            repoRace.save(r);
        }
    }

    private void addListeners() {

        raceButton.addClickListener(e -> {
            raceDialog.open();
        });

        roleButton.addClickListener(e -> {
            roleDialog.open();
        });

        addRoomButton.addClickListener(e-> {
            addRoomToLayout();
        });

        commitButton.addClickListener(e->{
            if (validateDungeon()) {
                createRooms();
                setNeighbours();
                Dungeon d = new Dungeon(nameField.getValue());
                repoDungeon.save(d);
                saveRoles(d.getId());
                saveRaces(d.getId());
                for (Room r : addedRooms) {
                    r.setDungeonID(d.getId());
                    repoRoom.save(r);
                }
                Notification.show("Der Dungeon wurde erstellt");
                nameField.clear();
                addedRooms.clear();

                UI.getCurrent().navigate("lobby");
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

