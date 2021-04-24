package com.de.dhbw.hb.mud.model;

public class Tupel<a> {
    a key;
    a value;

    public Tupel(a key, a value) {
        this.key = key;
        this.value = value;
    }

    public Tupel(){}

    public a getKey() {
        return key;
    }

    public void setKey(a key) {
        this.key = key;
    }

    public a getValue() {
        return value;
    }

    public void setValue(a value) {
        this.value = value;
    }
}
