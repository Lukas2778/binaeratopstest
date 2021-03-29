package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@PageTitle("Game")
public class GameView extends Div {

    ChatComponent chat;




    public GameView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {

        chat =new ChatComponent(publisher,messages,true);
        chat.setUsername(VaadinSession.getCurrent().getAttribute(UserDto.class).getName());

        setSizeFull();


        Label secondLabel = new Label("Platzhalter für eine Karte des Dungeons");
        Label thirdLabel = new Label("Platzhalter für Steuerungselemente");

        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(thirdLabel);
        innerLayout.setSizeFull();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(layout);
    }
}
