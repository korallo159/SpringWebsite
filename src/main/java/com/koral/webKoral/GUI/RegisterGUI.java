package com.koral.webKoral.GUI;

import com.koral.webKoral.Repo.ApplicationUserRepository;
import com.koral.webKoral.User.ApplicationUser;
import com.koral.webKoral.WebSecurityConfig;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.validation.constraints.Email;

@Route("register")
public class RegisterGUI extends VerticalLayout {

    private ApplicationUserRepository applicationUserRepository;
    private WebSecurityConfig webSecurityConfig;




    public RegisterGUI(ApplicationUserRepository applicationUserRepository, WebSecurityConfig webSecurityConfig){
        this.applicationUserRepository = applicationUserRepository;
        this.webSecurityConfig = webSecurityConfig;
        TextField textFieldLogin = new TextField("Podaj login");
        EmailField emailField = new EmailField("Podaj adres e-mail");
        PasswordField passwordField = new PasswordField("podaj haslo");
        PasswordField passwordFieldRepeat = new PasswordField("Potwórz hasło ");
        Button button = new Button("Kliknij");
        Label label = new Label();

        button.addClickListener(buttonClickEvent -> {
            String name = textFieldLogin.getValue();
           if(passwordField.getValue().equals(passwordFieldRepeat.getValue()) && this.applicationUserRepository.findByUsername(textFieldLogin.getValue()) == null) {
                ApplicationUser appUserUser = new ApplicationUser(textFieldLogin.getValue(), this.webSecurityConfig.passwordEncoder().encode(passwordField.getValue()), "ROLE_USER", emailField.getValue());
                this.applicationUserRepository.save(appUserUser);
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
}
