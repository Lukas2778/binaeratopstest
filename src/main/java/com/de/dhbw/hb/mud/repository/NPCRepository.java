package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.NPC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPCRepository extends JpaRepository<NPC, Long> {
    @Override
    List<NPC> findAll();
}
