package com.de.dhbw.hb.mud.service.dungeondata;


import com.de.dhbw.hb.mud.model.Item;
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



    public ArrayList<Item> findItemsInRoom(int roomID){
        ArrayList<Item> result = new ArrayList<>();
        for (Item e: itemRepository.findAll()) {
            if(roomID == e.getRoomID())
                result.add(e);
        }
        return result;
    }

}
