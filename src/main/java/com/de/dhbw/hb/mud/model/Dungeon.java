package com.de.dhbw.hb.mud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity(name ="dungeon")
public class Dungeon {
    // TODO: implementieren

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Transient
    private Room startRoom;

    @Transient
    private List<Room> rooms;

    @Transient
    private List<Avatar> avatars;

    //private Map map;

    public Dungeon(String name) {
        this.name = name;
    }

    public Dungeon(String name, Room startRoom, List<Room> rooms, List<Avatar> avatars) {
        this.name = name;
        this.startRoom = startRoom;
        this.rooms = rooms;
        this.avatars = avatars;
    }
}
