package com.de.dhbw.hb.mud.model;

public class RoomCords {
    private Room room;
    private Integer x;
    private Integer y;

    public RoomCords(Room room, Integer x, Integer y) {
        this.room = room;
        this.x = x;
        this.y = y;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
