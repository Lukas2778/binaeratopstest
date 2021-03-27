package com.de.dhbw.hb.mud;


import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.repository.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringComponent
public class DataGernerator {

    public CommandLineRunner loadData(@Autowired UserRepository repo){
        return args -> {
            repo.save(new UserDto("user","e@mail.com","password"));
        };
    }
}
