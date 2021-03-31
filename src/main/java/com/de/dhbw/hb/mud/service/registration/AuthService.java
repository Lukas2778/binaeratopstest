package com.de.dhbw.hb.mud.service.registration;

import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.repository.UserRepository;
import com.de.dhbw.hb.mud.service.registration.exception.AuthException;
import com.de.dhbw.hb.mud.service.registration.exception.IllegalMailException;
import com.de.dhbw.hb.mud.service.registration.exception.RegisterException;
import com.de.dhbw.hb.mud.views.lobby.CreateAvatarDialog;
import com.de.dhbw.hb.mud.views.Konfigurator.AvatarKonfiguratorView;
import com.de.dhbw.hb.mud.views.about.AboutView;
import com.de.dhbw.hb.mud.views.chat.ChatView;
import com.de.dhbw.hb.mud.views.dungeons.DungeonView;
import com.de.dhbw.hb.mud.views.game.GameView;
import com.de.dhbw.hb.mud.views.lobby.LobbyView;
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
            VaadinSession.getCurrent().setAttribute("userID", 0L);
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

        //routes.add(new AuthorizedRoute("helloworld","Hello World", HelloWorldView.class));
        //routes.add(new AuthorizedRoute("test","Hello World", TestView.class));
        routes.add(new AuthorizedRoute("about","Hello World", AboutView.class));
        routes.add(new AuthorizedRoute("chat","Hello World", ChatView.class));
        routes.add(new AuthorizedRoute("AvatarErstellung","Avatar erstellen", CreateAvatarDialog.class));
        routes.add(new AuthorizedRoute("Konfigurator","Avatar konfigurieren", AvatarKonfiguratorView.class));
        routes.add(new AuthorizedRoute("Dungeon","Dungeon", DungeonView.class));
        routes.add(new AuthorizedRoute("lobby","Lobby", LobbyView.class));
        routes.add(new AuthorizedRoute("game", "Game", GameView.class));
        return routes;
    }

    public void register(String username, String password, String eMail) throws RegisterException, IllegalMailException {
        List<UserDto> list =repo.findAll();
        for (UserDto user:
             list) {
            if(user.getName().equals(username)){
                throw new RegisterException();
            }
        }
        try {
            repo.save(new UserDto(username,eMail,password));
        }catch (Exception e){
            throw  new IllegalMailException();
        }

    }
}
