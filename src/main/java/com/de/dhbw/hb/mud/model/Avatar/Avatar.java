package com.de.dhbw.hb.mud.model.Avatar;

import com.de.dhbw.hb.mud.model.Race;
import com.de.dhbw.hb.mud.model.Role;

import javax.persistence.*;

@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private Long race;

    private Long role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long PlayerId;

    private Long DungeonId;

    public Avatar(Long aPlayerId, Long aDungeonId ,String aName, Race aRace, Role aRole, Gender aGender){
        PlayerId = aPlayerId;
        DungeonId = aDungeonId;
        name = aName;
        race = aRace.getId();
        role = aRole.getId();
        gender = aGender;
    }
    public Avatar(){}

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

    public Long getRace() {
        return race;
    }

    public void setRace(Long race) {
        this.race = race;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(Long playerId) {
        PlayerId = playerId;
    }

    public Long getDungeonId() {
        return DungeonId;
    }

    public void setDungeonId(Long dungeonId) {
        DungeonId = dungeonId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
