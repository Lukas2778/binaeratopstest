package com.de.dhbw.hb.mud.service.registration;

import com.de.dhbw.hb.mud.model.Dungeon;
import com.de.dhbw.hb.mud.repository.DungeonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DungeonService {

    @Autowired
    private DungeonRepository dungeonRepository;

    public DungeonService(DungeonRepository dungeonRepository){
        this.dungeonRepository=dungeonRepository;
    }

    public List<Dungeon> findAll() {
        return dungeonRepository.findAll();
    }

    public void delete(Dungeon dungeon){this.dungeonRepository.delete(dungeon);}
}
