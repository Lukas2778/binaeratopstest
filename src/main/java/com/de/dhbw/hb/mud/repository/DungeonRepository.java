package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Dungeon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DungeonRepository extends JpaRepository<Dungeon, Long> {
    @Override
    List<Dungeon> findAll();

}
