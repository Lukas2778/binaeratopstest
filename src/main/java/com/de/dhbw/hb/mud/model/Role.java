package com.de.dhbw.hb.mud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private Long dungeonID;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDungeonID() {
        return dungeonID;
    }

    public void setDungeonID(Long dungeonID) {
        this.dungeonID = dungeonID;
    }
}
