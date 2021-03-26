package com.de.dhbw.hb.mud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name ="test_table")
public class TestClass {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String race;

    private int power;

    private int size;

    public TestClass(){}

    public TestClass(String name, String race, int power, int size) {
        this.name = name;
        this.race = race;
        this.power = power;
        this.size = size;
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
