package com.de.dhbw.hb.mud.views.chat;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;


@SuppressWarnings("deprecation")
@CssImport("./views/helloworld/chat-view.css")
@PageTitle("Öffentlicher Chat")

public class ChatView extends Div {

    private ChatComponent chat;

    public ChatView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        chat=new ChatComponent(publisher,messages,false);
        setSizeFull();
        askUsername();

    }

    private void askUsername()  {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");
        layout.add(usernameField, startButton);
        startButton.addClickListener(click -> {
            chat.setUsername(usernameField.getValue());
            remove(layout);
            chat.showChat();
            add(chat);
        } );
        add(layout);
    }


}
