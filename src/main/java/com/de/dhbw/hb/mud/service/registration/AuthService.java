package com.de.dhbw.hb.mud.service.registration;

import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.repository.UserRepository;
import com.de.dhbw.hb.mud.service.registration.exception.AuthException;
import com.de.dhbw.hb.mud.service.registration.exception.RegisterException;
import com.de.dhbw.hb.mud.views.TestView;
import com.de.dhbw.hb.mud.views.about.AboutView;
import com.de.dhbw.hb.mud.views.helloworld.HelloWorldView;
import com.de.dhbw.hb.mud.views.main.MainView;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    public void authenticate(String name, String password) throws AuthException {
        List<UserDto> all=repo.findAll();
        UserDto user = null;
        for (UserDto u:
             all) {
            if (u.getName() .equals(name)){
                user =u;
                break;
            }
        }

        if(user!=null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(UserDto.class, user);
            createRouts();

        }else{
            throw new AuthException();
        }

    }

    private void createRouts() {
        getRouts().stream()
        .forEach(r->
                RouteConfiguration.forSessionScope().setRoute(r.getRout(),r.getView(), MainView.class));
    }

    public List<AuthorizedRoute> getRouts(){
        ArrayList<AuthorizedRoute> routes =new ArrayList<>();

        routes.add(new AuthorizedRoute("helloworld","Hello World", HelloWorldView.class));
        routes.add(new AuthorizedRoute("test","Hello World", TestView.class));
        routes.add(new AuthorizedRoute("about","Hello World", AboutView.class));


        return routes;
    };

    public void register(String username, String password, String eMail) throws RegisterException {
        List<UserDto> list =repo.findAll();
        for (UserDto user:
             list) {
            if(user.getName().equals(username)){
                throw new RegisterException();
            }
        }
        repo.save(new UserDto(username,eMail,password));
    }
}