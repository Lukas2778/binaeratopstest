package com.de.dhbw.hb.mud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity(name ="room")
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long dungeonID;

    private String type;

    public Long getDungeonID() {
        return dungeonID;
    }

    public void setDungeonID(Long dungeonID) {
        this.dungeonID = dungeonID;
    }

    @Transient
    private List<Item> items;

    @Transient
    private List<NPC> npcs;

    private boolean questAvailable;

    private int northRoomID;
    private int eastRoomID;
    private int southRoomID;
    private int westRoomID;

    @Transient
    private Room northRoom;
    @Transient
    private Room eastRoom;
    @Transient
    private Room southRoom;
    @Transient
    private Room westRoom;

    @Transient
    private int[] coordinates;

    public Room() {}

    public Room(String name, String type, int[] coordinates) {
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<NPC> getNPCs() {
        return npcs;
    }

    public void setNPCs(List<NPC> npcs) {
        this.npcs = npcs;
    }

    public boolean isQuestAvailable() {
        return questAvailable;
    }

    public void setQuestAvailable(boolean questAvailable) {
        this.questAvailable = questAvailable;
    }

    public int getNorthRoomID() {
        return northRoomID;
    }

    public void setNorthRoomID(int northRoomID) {
        this.northRoomID = northRoomID;
    }

    public int getEastRoomID() {
        return eastRoomID;
    }

    public void setEastRoomID(int eastRoomID) {
        this.eastRoomID = eastRoomID;
    }

    public int getSouthRoomID() {
        return southRoomID;
    }

    public void setSouthRoomID(int southRoomID) {
        this.southRoomID = southRoomID;
    }

    public int getWestRoomID() {
        return westRoomID;
    }

    public void setWestRoomID(int westRoomID) {
        this.westRoomID = westRoomID;
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public void setNorthRoom(Room northRoom) {
        this.northRoom = northRoom;
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public void setEastRoom(Room eastRoom) {
        this.eastRoom = eastRoom;
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public void setSouthRoom(Room southRoom) {
        this.southRoom = southRoom;
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public void setWestRoom(Room westRoom) {
        this.westRoom = westRoom;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}
