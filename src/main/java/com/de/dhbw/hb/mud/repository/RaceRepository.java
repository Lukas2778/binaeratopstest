package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    @Override
    List<Race> findAll();
}
