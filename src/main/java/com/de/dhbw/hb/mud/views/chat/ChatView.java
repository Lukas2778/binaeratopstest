package com.de.dhbw.hb.mud.views.chat;

import com.de.dhbw.hb.mud.model.ChatMessage;
import com.de.dhbw.hb.mud.model.MessageList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;


@SuppressWarnings("deprecation")
@CssImport("./views/helloworld/chat-view.css")
//@Route(value = "hello", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Vaadin Chat")

public class ChatView extends VerticalLayout {

    private String username;
    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    public ChatView(UnicastProcessor<ChatMessage> publisher,
                    Flux<ChatMessage> messages) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

askUsername();



    }

    private void askUsername()  {
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField();
        Button startButton = new Button("Start chat");

        layout.add(usernameField, startButton);

        startButton.addClickListener(click -> {
            username = usernameField.getValue();
            remove(layout);
            showChat();
        } );

        add(layout);

    }

    private void showChat()
    {
     MessageList messageList = new MessageList();

     add(messageList, createInputLayout());

     expand(messageList);
     messages.subscribe(message-> {
         messageList.add(
                 new Paragraph(message.getFrom()+": " +
                         message.getMessage())
         );
     });
    }


    private Component createInputLayout() {
       HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
       TextField messageField = new TextField();
       Button sendButton = new Button("Send");
       sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

       layout.add(messageField, sendButton);
        layout.expand(messageField);

        sendButton.addClickListener(click -> {
           publisher.onNext(new ChatMessage(username, messageField.getValue()));
           messageField.clear();
           messageField.focus();
        });
        messageField.focus();

       return layout;
    }

}
