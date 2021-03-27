package com.de.dhbw.hb.mud.model;

import com.de.dhbw.hb.mud.constants.Region;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity(name ="room")
public class Room {
    // TODO: implementieren

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Transient
    private Region type;

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
    private int[] coordinates;

    public Room(String name, Region type, List<Item> items, List<NPC> npcs, boolean questAvailable, int northRoomID, int eastRoomID, int southRoomID, int westRoomID, int[] coordinates) {
        this.name = name;
        this.type = type;
        this.items = items;
        this.npcs = npcs;
        this.questAvailable = questAvailable;
        this.northRoomID = northRoomID;
        this.eastRoomID = eastRoomID;
        this.southRoomID = southRoomID;
        this.westRoomID = westRoomID;
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

    public Region getType() {
        return type;
    }

    public void setType(Region type) {
        this.type = type;
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

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}
