package com.koral.webKoral.gui;

import com.koral.webKoral.service.UserService;
import com.koral.webKoral.model.Token;
import com.koral.webKoral.repo.ApplicationUserRepository;
import com.koral.webKoral.model.ApplicationUser;
import com.koral.webKoral.repo.TokenRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.UUID;

@Route("register")
public class RegisterGUI extends VerticalLayout {

    private ApplicationUserRepository applicationUserRepository;
    private TokenRepo tokenRepo;
    private UserService userService;



    public RegisterGUI(ApplicationUserRepository applicationUserRepository, TokenRepo tokenRepo, UserService userService){
        this.applicationUserRepository = applicationUserRepository;
        this.userService = userService;
        TextField textFieldLogin = new TextField("Podaj login");
        EmailField emailField = new EmailField("Podaj adres e-mail");
        PasswordField passwordField = new PasswordField("podaj haslo");
        PasswordField passwordFieldRepeat = new PasswordField("Potwórz hasło ");
        Button button = new Button("Kliknij");
        Label label = new Label();

        button.addClickListener(buttonClickEvent -> {
            String name = textFieldLogin.getValue();
           if(passwordField.getValue().equals(passwordFieldRepeat.getValue()) && this.applicationUserRepository.findByUsername(textFieldLogin.getValue()) == null) {
               userService.addUser(textFieldLogin.getValue(), passwordField.getValue(), emailField.getValue(), "ROLE_USER");
                label.setText("Zarejestrowano!");
               Notification notification = new Notification("Użytkownik "+ textFieldLogin.getValue() + " dodany!");
               notification.open();
            }
            else if(!passwordField.getValue().equals(passwordFieldRepeat.getValue())){
               label.setText("Hasła nie są takie same!");
           }
            else if(this.applicationUserRepository.findByUsername(textFieldLogin.getValue()) != null)
                label.setText("Taki użytkownik już istnieje!");

        });
        add(textFieldLogin, emailField, passwordField, passwordFieldRepeat, button, label);

    }

    private void sendToken(ApplicationUser applicationUser) {
        String tokenValue = UUID.randomUUID().toString();

        Token token = new Token();
        token.setValue(tokenValue);
        token.setApplicationUser(applicationUser);
        tokenRepo.save(token);
        String url = "http://localhost:8080=" + tokenValue;


    }
}
