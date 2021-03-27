package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Dungeon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DungeonRepository extends CrudRepository<Dungeon, Long> {

    @Override
    List<Dungeon> findAll();
}
