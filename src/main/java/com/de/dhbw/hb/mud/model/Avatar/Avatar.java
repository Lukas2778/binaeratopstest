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
    @Transient
    private Race race;
    @Transient
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long PlayerId;



    private Long DungeonId;

    public Avatar(Long aPlayerId, Long aDungeonId ,String aName, Race aRace, Role aRole, Gender aGender){
        PlayerId = aPlayerId;
        DungeonId = aDungeonId;
        name = aName;
        race = aRace;
        role = aRole;
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

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
