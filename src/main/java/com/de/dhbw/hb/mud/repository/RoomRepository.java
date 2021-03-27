package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Override
    List<Room> findAll();
}