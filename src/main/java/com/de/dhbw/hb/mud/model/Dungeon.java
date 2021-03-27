package com.de.dhbw.hb.mud.model;

import com.de.dhbw.hb.mud.model.Avatar.Avatar;
import com.vaadin.flow.server.VaadinSession;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity(name ="dungeon")
public class Dungeon {

    @Id
    @GeneratedValue
    private Long id;

    private Long creatorID;

    private String name;

    @Transient
    private Room startRoom;

    @Transient
    private List<Room> rooms;

    @Transient
    private List<Avatar> avatars;

    //private Map map;
    public Dungeon(){}
    public Dungeon(String name) {
        this.name = name;
        this.creatorID = VaadinSession.getCurrent().getAttribute(UserDto.class).getId();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public Long getId() {
        return id;
    }

    public Long getCreatorID() {
        return creatorID;
    }

    public void addAvatar(Avatar avatar) {
        avatars.add(avatar);
    }

    public void removeAvatar(Avatar avatar) {
        avatars.remove(avatar);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getStartRoom() {
        return startRoom;
    }

    public void setStartRoom(Room startRoom) {
        this.startRoom = startRoom;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }
}
