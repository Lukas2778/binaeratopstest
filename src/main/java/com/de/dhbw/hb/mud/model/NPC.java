package com.de.dhbw.hb.mud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NPC {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long RoomID;

    private String race;

    private String description;

    public NPC(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoomID() {
        return RoomID;
    }

    public void setRoomID(Long roomID) {
        RoomID = roomID;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
