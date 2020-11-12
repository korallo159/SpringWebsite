package com.koral.webKoral.gui;
import com.koral.webKoral.repo.ApplicationUserRepository;
import com.koral.webKoral.model.ApplicationUser;
import com.koral.webKoral.WebSecurityConfig;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("databasemanager")
public class AdminPanelGUI extends VerticalLayout {
    private ApplicationUserRepository applicationUserRepository;
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    public AdminPanelGUI(ApplicationUserRepository applicationUserRepository, WebSecurityConfig webSecurityConfig){
        this.webSecurityConfig = webSecurityConfig;
        this.applicationUserRepository = applicationUserRepository;
        Accordion accordion = new Accordion();
        VerticalLayout verticalLayoutAdduser = new VerticalLayout();
        TextField textFieldUsername = new TextField("Username");
        TextField textFieldPassword = new TextField("Password");
        EmailField emailField = new EmailField("Email");
        ListBox<String> listBoxRole = new ListBox<>();
        listBoxRole.setItems("ROLE_USER", "ROLE_ADMIN");
        Button button = new Button("Dodaj uzytkownika");
        verticalLayoutAdduser.add(textFieldUsername, textFieldPassword, emailField, listBoxRole, button);
        button.addClickListener(buttonClickEvent -> {
            if(this.applicationUserRepository.findByUsername(textFieldUsername.getValue()) == null) {
               ApplicationUser appUserUser = new ApplicationUser(textFieldUsername.getValue(),
               this.webSecurityConfig.passwordEncoder().encode(textFieldPassword.getValue()),
               listBoxRole.getValue(), emailField.getValue()
                );
                this.applicationUserRepository.save(appUserUser);
                UI.getCurrent().getPage().reload();
            }
            else {
                Notification notification = new Notification("Błąd! Użytkownik " + textFieldUsername.getValue() + " już istnieje!");
                notification.open();
            }
        });


        VerticalLayout verticalLayoutRemove = new VerticalLayout();
        NumberField numberField = new NumberField("Podaj ID");
        Button buttonDelete = new Button("Usun uzytkownika");
        verticalLayoutRemove.add(numberField,buttonDelete);
        buttonDelete.addClickListener(buttonClickEvent -> {
            this.applicationUserRepository.deleteById(numberField.getValue().longValue());
            Notification notification = new Notification("użytkownik" + textFieldUsername.getValue() + " usunięty!");
            notification.open();
        });

        HorizontalLayout horizontalLayoutGrid = new HorizontalLayout();
        Grid<ApplicationUser> grid = new Grid<>(ApplicationUser.class);
        grid.setItems(applicationUserRepository.findAll());
        horizontalLayoutGrid.add(grid);


        accordion.add("Dodaj użytkownika", verticalLayoutAdduser);
        accordion.add("Usuń użytkownika", verticalLayoutRemove);
        accordion.add("Zobacz listę użytkowników", horizontalLayoutGrid);
        add(accordion);
        accordion.setWidthFull();

    }
}
