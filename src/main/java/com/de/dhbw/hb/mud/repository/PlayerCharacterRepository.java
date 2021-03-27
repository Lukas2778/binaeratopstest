package com.de.dhbw.hb.mud.repository;

import com.de.dhbw.hb.mud.model.Avatar.Avatar;
import com.de.dhbw.hb.mud.model.TestClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerCharacterRepository extends CrudRepository<Avatar,Long >{
    @Override
    List<Avatar> findAll();
}
