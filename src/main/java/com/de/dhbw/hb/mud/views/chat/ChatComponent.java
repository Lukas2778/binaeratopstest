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
import com.vaadin.flow.component.textfield.TextField;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@CssImport("./views/helloworld/chat-view.css")
public class ChatComponent extends VerticalLayout {

    private String username;

    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;

    public ChatComponent(UnicastProcessor<ChatMessage> publisher,
                         Flux<ChatMessage> messages,
                         boolean showChat) {
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        if (showChat)
            showChat();

    }

    public void showChat() {
        MessageList messageList = new MessageList();

        add(messageList, createInputLayout());

        expand(messageList);
        messages.subscribe(message -> getUI().ifPresent(ui ->
                ui.access(() ->
                        messageList.add(
                                new Paragraph(message.getFrom() + ": " +
                                        message.getMessage())
                        ))

        ));
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {return username;}
}

