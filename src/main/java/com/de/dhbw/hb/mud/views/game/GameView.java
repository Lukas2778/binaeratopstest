package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@PageTitle("Game")
public class GameView extends Div {

    ChatComponent chat;




    public GameView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {

        TextField role=new TextField("Rolle");
        role.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);

        chat =new ChatComponent(publisher,messages,true);

        //Später mit dem Parameter Dungeonmaster ändern
        if(true){
            chat.setUsername("Dungeon Master");
        } else{
            chat.setUsername(VaadinSession.getCurrent().getAttribute(UserDto.class).getName());
        }

        role.setValue(chat.getUsername());
        role.addKeyPressListener(Key.ENTER, e -> {
            chat.setUsername(role.getValue());
            publisher.onNext(new ChatMessage("TestUsername", "Hallo"));


        });

        setSizeFull();



        Label secondLabel = new Label("Platzhalter für eine Karte des Dungeons");
        //label thirdLabel = new Label("Rolle:                                           ");


        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(role);
        innerLayout.setSizeFull();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(layout);
    }
}
