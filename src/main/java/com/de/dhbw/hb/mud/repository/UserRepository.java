package com.de.dhbw.hb.mud.repository;


import com.de.dhbw.hb.mud.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDto,Long> {

    @Override
    List<UserDto>findAll();

    UserDto findByName(String name);

}