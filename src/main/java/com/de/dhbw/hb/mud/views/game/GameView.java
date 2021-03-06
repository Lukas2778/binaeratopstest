package com.de.dhbw.hb.mud.views.game;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.Room;
import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.service.dungeondata.DungeonDataService;
import com.de.dhbw.hb.mud.views.chat.ChatComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.Random;
import java.util.stream.Collectors;

@CssImport("./views/helloworld/game-view.css")
@PageTitle("Game")
public class GameView extends VerticalLayout implements HasUrlParameter<Long>{

    ChatComponent chat;
    private long dungeonID;
    private Room currentRoom;

    private ComboBox<Room> moveCombobox = new ComboBox<>("bewegen");

    @Autowired
    DungeonDataService dungeonDataService;


public GameView(UnicastProcessor<ChatMessage> publisher,
                Flux<ChatMessage> messages, DungeonDataService dungeonDataService){
    add(new Button("eintreten", e->showGame(publisher, messages, dungeonDataService)));

}

    public void showGame(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages, DungeonDataService dungeonDataService) {

        this.dungeonDataService = dungeonDataService;
        this.currentRoom = dungeonDataService.getStartRoom(dungeonID);

        removeAll();;
        chat =new ChatComponent(publisher,messages,true);

        //TODO Sp??ter mit dem Parameter Dungeonmaster ??ndern

        moveCombobox.setItemLabelGenerator(Room :: getName);
        moveCombobox.setItems(dungeonDataService.getNeighborRooms(currentRoom.getId()));


        VerticalLayout controllsView =new VerticalLayout();
        HorizontalLayout ButtonView =new HorizontalLayout();

        if(dungeonDataService.isDungeonMaster(dungeonID, VaadinSession.getCurrent().getAttribute(UserDto.class).getId())){
            chat.setUsername("Dungeon Master");
            TextField role=new TextField("Rolle");
            role.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
            role.setValue(chat.getUsername());
            role.addKeyPressListener(Key.ENTER, e -> {
                chat.setUsername(role.getValue());
                //publisher.onNext(new ChatMessage("TestUsername", "Hallo"));

            });
            IntegerField diceLimit =new IntegerField("W??rfel bis:");

            Button dice =new Button("W??rfel");
            dice.addClickListener(e -> {dice.setText(String.valueOf(new Random().nextInt(diceLimit.getValue())+1));});

            controllsView.add(role,new HorizontalLayout(diceLimit,dice));

        } else{

            long userNameID= VaadinSession.getCurrent().getAttribute(UserDto.class).getId();

            chat.setUsername(dungeonDataService.getAvatarName(dungeonID, userNameID));
            TextField role=new TextField("Rolle");
            role.setValue(chat.getUsername());
            role.setReadOnly(true);
            role.addThemeVariants();



            controllsView.add(role );




            StringBuilder sB = new StringBuilder();
            dungeonDataService.findItemsInRoom(12).stream().map(item -> item.getName()).forEach(sB::append);

            //Problem -> Keine Abs??tze M??glich
            //String itemInRoom = dungeonDataService.findItemsInRoom(currentRoom.getId()).stream().map(item -> item.getName()).collect(Collectors.joining(", "));



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


            //if(itemInRoom.isEmpty()){
            //    itemInRoom = "keine Gegenst??nde";
            //}
            //String npcInRoom = dungeonDataService.findNPCsInRoom(currentRoom.getId()).stream().map(npc -> npc.getName()).collect(Collectors.joining(", "));
            //if(npcInRoom.isEmpty()){
            //    npcInRoom = "Du bist alleine";
            //}else{
            //    String temp = npcInRoom;
            //    npcInRoom = "Dir f??llt auf, dass du nicht alleine bist "  + temp + " kannst du erkennen";
            //}

            //String finalItemInRoom = itemInRoom;

            Button UmschauenItems = new Button("Gegenst??nde suchen", e -> publisher.onNext(
                    new ChatMessage("INFO", "Du befindest dich in "  + currentRoom.getName() +
                    " und beim Umschauen erblickst du " + findItems() + " auf dem Boden liegen.")));

            //String finalNpcInRoom = npcInRoom;
            Button UmschauenNPC = new Button("NPC suchen", e -> publisher.onNext(
                    new ChatMessage("INFO",findNPC())));
            Button Ansprechen = new Button("Aufheben", e -> publisher.onNext(
                    new ChatMessage("Items: ", dungeonDataService.findItemsInRoom(12).get(0).getName())));
            Button Bewegen = new Button("Aufheben", e -> publisher.onNext(
                    new ChatMessage("Items: ", dungeonDataService.findItemsInRoom(12).get(0).getName())));


            ButtonView.add( UmschauenItems, UmschauenNPC );


            controllsView.add(ButtonView,moveCombobox,new Button("laufe nach" , e->{
                currentRoom = moveCombobox.getValue();
                moveCombobox.setItems(dungeonDataService.getNeighborRooms(currentRoom.getId()));
            }));

        }



        setSizeFull();



        Label secondLabel = new Label("Platzhalter f??r eine Karte des Dungeons");
        //Label thirdLabel = new Label("Rolle: " + chat.getUsername());


        SplitLayout innerLayout = new SplitLayout();
        innerLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        innerLayout.addToPrimary(secondLabel);
        innerLayout.addToSecondary(controllsView);
        innerLayout.setSizeFull();

        SplitLayout layout = new SplitLayout();
        layout.addToPrimary(chat);
        layout.addToSecondary(innerLayout);
        layout.setSizeFull();


        add(new HorizontalLayout(new Text(dungeonDataService.getDungeonName(dungeonID)), new Button("Spiel verlassen",
                e-> UI.getCurrent().navigate("about"))),layout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long dungeonID)
    {
        this.dungeonID = dungeonID;
    }

    private String findItems(){
        String result=  dungeonDataService.findItemsInRoom(currentRoom.getId()).stream().map(item -> item.getName())
                .collect(Collectors.joining(", "));
        if(result.isEmpty()){
            result = "keine Gegenst??nde";
        }
        return result;
    }

    private  String findNPC(){
        String npcInRoom = dungeonDataService.findNPCsInRoom(currentRoom.getId()).stream().map(npc
                -> npc.getName()).collect(Collectors.joining(", "));
        if(npcInRoom.isEmpty()){
            npcInRoom = "Du bist alleine!";
        }else{
            String temp = npcInRoom;
            npcInRoom = "Dir f??llt auf, dass du nicht alleine bist. "  + temp + " kannst du erkennen.";
        }
        return npcInRoom;
    }

}
