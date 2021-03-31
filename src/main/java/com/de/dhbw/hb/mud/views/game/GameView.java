package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.service.dungeondata.DungeonDataService;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Random;

@CssImport("./views/helloworld/game-view.css")
@PageTitle("Game")
public class GameView extends VerticalLayout {

    ChatComponent chat;

    @Autowired
    DungeonDataService dungeonDataService;




    public GameView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages, DungeonDataService dungeonDataService) {

        this.dungeonDataService = dungeonDataService;



        chat =new ChatComponent(publisher,messages,true);

        //TODO Später mit dem Parameter Dungeonmaster ändern

        VerticalLayout controllsView =new VerticalLayout();
        HorizontalLayout ButtonView =new HorizontalLayout();

        if(true){
            chat.setUsername("Dungeon Master");
            TextField role=new TextField("Rolle");
            role.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
            role.setValue(chat.getUsername());
            role.addKeyPressListener(Key.ENTER, e -> {
                chat.setUsername(role.getValue());
                //publisher.onNext(new ChatMessage("TestUsername", "Hallo"));

            });
            IntegerField diceLimit =new IntegerField("Würfel bis:");

            Button dice =new Button("Würfel");
            dice.addClickListener(e -> {dice.setText(String.valueOf(new Random().nextInt(diceLimit.getValue())+1));});

            controllsView.add(role,new HorizontalLayout(diceLimit,dice));

        } else{
            chat.setUsername(VaadinSession.getCurrent().getAttribute(UserDto.class).getName());
            TextField role=new TextField("Rolle");
            role.setValue(chat.getUsername());
            role.setReadOnly(true);
            role.addThemeVariants();



            controllsView.add(role );




            StringBuilder sB = new StringBuilder();
            dungeonDataService.findItemsInRoom(12).stream().map(item -> item.getName()).forEach(sB::append);

            //Problem -> Keine Absätze Möglich
            String result = dungeonDataService.findItemsInRoom(12).stream().map(item -> item.getName()).collect(Collectors.joining("\n"));


//            private void itemsAusgeben() {
//                for ( int i = 0; i < dungeonDataService.findItemsInRoom(12)
//                        .size(); i++ )
//                {
//
//                    publisher.onNext(new ChatMessage("Items: ",
//                                                     dungeonDataService.findItemsInRoom(12)
//                                                             .get(i)
//                                                             .getName()));
//                }
//            }



            Button Umschauen = new Button("Umschauen", e -> publisher.onNext(new ChatMessage("Items: ", result)));
            Button Aufheben = new Button("Aufheben", e -> publisher.onNext(new ChatMessage("Items: ", dungeonDataService.findItemsInRoom(12).get(0).getName())));
            Button Ansprechen = new Button("Aufheben", e -> publisher.onNext(new ChatMessage("Items: ", dungeonDataService.findItemsInRoom(12).get(0).getName())));
            Button Bewegen = new Button("Aufheben", e -> publisher.onNext(new ChatMessage("Items: ", dungeonDataService.findItemsInRoom(12).get(0).getName())));
            ButtonView.add( Umschauen, Aufheben );

        }



        setSizeFull();



        Label secondLabel = new Label("Platzhalter für eine Karte des Dungeons");
        //Label thirdLabel = new Label("Rolle: " + chat.getUsername());


        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(controllsView, ButtonView);
        innerLayout.setSizeFull();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(new HorizontalLayout(new Text("Dungeonname"), new Button("Spiel verlassen",
                e-> UI.getCurrent().navigate("about"))),layout);
    }

}
