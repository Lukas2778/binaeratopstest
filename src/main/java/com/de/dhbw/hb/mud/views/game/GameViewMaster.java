package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@PageTitle("Game")
public class GameViewMaster extends Div {
    ChatComponent chat;






    public GameViewMaster(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {

        TextField role=new TextField("Rolle");
        chat =new ChatComponent(publisher,messages,true);
        chat.setUsername("Dungeon Master");
        role.addInputListener(e->{
            chat.setUsername(role.getValue());

        } );
        setSizeFull();


        Label secondLabel = new Label("Platzhalter für eine Karte des Dungeons");
        Label thirdLabel = new Label("Platzhalter für Steuerungselemente");

        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(thirdLabel,role);
        innerLayout.setSizeFull();



        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(layout);
    }
}
