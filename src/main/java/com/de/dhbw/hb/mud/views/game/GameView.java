package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@PageTitle("Game")
public class GameView extends VerticalLayout {

    ChatComponent chat;




    public GameView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {



        chat =new ChatComponent(publisher,messages,true);

        //TODO Später mit dem Parameter Dungeonmaster ändern

        Div controllsView=new Div();
        if(true){
            chat.setUsername("Dungeon Master");
            TextField role=new TextField("Rolle");
            role.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
            role.setValue(chat.getUsername());
            role.addKeyPressListener(Key.ENTER, e -> {
                chat.setUsername(role.getValue());
                //publisher.onNext(new ChatMessage("TestUsername", "Hallo"));

            });

            controllsView.add(role);

        } else{
            chat.setUsername(VaadinSession.getCurrent().getAttribute(UserDto.class).getName());
        }



        setSizeFull();



        Label secondLabel = new Label("Platzhalter für eine Karte des Dungeons");
        //label thirdLabel = new Label("Rolle:                                           ");


        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(controllsView);
        innerLayout.setSizeFull();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(new HorizontalLayout(new Text("Dungeonname"), new Button("Spiel verlassen",
                e-> UI.getCurrent().navigate("about"))),layout);
    }
}
