package com.koral.webKoral.chat;

import com.koral.webKoral.chat.ChatMessage;
import com.koral.webKoral.chat.MessageList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.w3c.dom.css.RGBColor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

@Route("chat")
@CssImport("./chat.css")
@PWA(name = "Vaadin Chat", shortName = "Vaadin Chat")
@Push
public class ChatGUI extends VerticalLayout {
    private final UnicastProcessor<ChatMessage> publisher;
    private final Flux<ChatMessage> messages;
    private String username;

    public ChatGUI(UnicastProcessor<ChatMessage> publisher, Flux<ChatMessage> messages){
        this.publisher = publisher;
        this.messages = messages;
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("main-view");

        H1 header = new H1("websockets vaadin example");
        header.getElement().getThemeList().add("dark");
        add(header);
        askForUsername();
    }

    private void askForUsername(){
        HorizontalLayout layout = new HorizontalLayout();
        TextField usernameField = new TextField("Nazwa użytkownika");
        Button startButton = new Button("Rozpocznij chat");
        add(usernameField, startButton);
        add(layout);
        startButton.addClickListener(e -> {
            username = usernameField.getValue();
            removeAll();
            showChat();
        });
    }

    private void showChat(){
        MessageList messageList = new MessageList();
        add(messageList, createInputLayout());
        expand(messageList);
        //Event ktory tworzy nowy paragraf, z getForm i getMessage
        //UI, zeby wiadomosci byly odbierane przez kazdego ktos jest na czacie
        messages.subscribe(message -> getUI().ifPresent(ui ->
                ui.access(() ->
                messageList.add(
                        new Paragraph( message.getForm() + ": " + message.getMessage())))));
    }

    private Component createInputLayout(){
    HorizontalLayout layout = new HorizontalLayout();

    TextField messageField = new TextField();
    Button sendButton = new Button("send");
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickListener(e ->{
           publisher.onNext(new ChatMessage(username, messageField.getValue()));
           messageField.clear();
           messageField.focus();
        });
        messageField.focus();
        layout.add(messageField, sendButton);
        layout.setWidth("100%");
        layout.expand(messageField);
        return layout;

    }



}
