package com.de.dhbw.hb.mud.service.dungeondata;


import com.de.dhbw.hb.mud.model.Avatar.Avatar;
import com.de.dhbw.hb.mud.model.Item;
import com.de.dhbw.hb.mud.model.Room;
import com.de.dhbw.hb.mud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DungeonDataService {

    @Autowired
    private DungeonRepository dungeonRepository;
    @Autowired
    private NPCRepository npcRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private PlayerCharacterRepository playerCharacterRepository;



    public ArrayList<Item> findItemsInRoom(int roomID){
        ArrayList<Item> result = new ArrayList<>();
        for (Item e: itemRepository.findAll()) {
            if(roomID == e.getRoomID())
                result.add(e);
        }
        return result;
    }
    public boolean isDungeonMaster(long dungeonID, long playerID ){
        return dungeonRepository.findById(dungeonID).get().getCreatorID() == playerID;
    }



    public ArrayList<Room> getRooms(long dungeonID){
        ArrayList<Room> result= new ArrayList<>();
        for (Room e: roomRepository.findAll()) {
            if(dungeonID == e.getDungeonID())
                result.add(e);
        }
        return result;
    }

    public String AvatarName(long dungeonID,long userID){
       ArrayList<Avatar> result= new ArrayList<>();
        for (Avatar e: playerCharacterRepository.findAll()) {
            if(e.getPlayerId()==userID && e.getDungeonId()==dungeonID)
                return e.getName();
        }
        return null;
    }

    public Room getStartRoom(long dungeonID){
        //TODO korrekte lösung
        Room returnr = null;
        long flag=100000000;
        for (Room e: roomRepository.findAll()) {
            if(flag < e.getId())
                returnr  =e;
        }
        return returnr;
    }

    public ArrayList<Room> getNeighborRooms(long roomID){
        ArrayList<Room> result= new ArrayList<>();
        if(roomRepository.findById(roomID).get().getEastRoomID() !=null)
            result.add(roomRepository.findById(roomRepository.findById(roomID).get().getEastRoomID()).get());
        if(roomRepository.findById(roomID).get().getWestRoom() !=null)
            result.add(roomRepository.findById(roomRepository.findById(roomID).get().getWestRoomID()).get());
        if(roomRepository.findById(roomID).get().getNorthRoom() !=null)
            result.add(roomRepository.findById(roomRepository.findById(roomID).get().getNorthRoomID()).get());
        if(roomRepository.findById(roomID).get().getSouthRoom() !=null)
            result.add(roomRepository.findById(roomRepository.findById(roomID).get().getSouthRoomID()).get());
        return result;
    }
}
