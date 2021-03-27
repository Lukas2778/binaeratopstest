package com.de.dhbw.hb.mud.views.login;

import com.de.dhbw.hb.mud.service.registration.AuthService;
import com.de.dhbw.hb.mud.service.registration.exception.RegisterException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value="register")
@PageTitle("register")
public class RegistrationView extends VerticalLayout {

    @Autowired
    private AuthService authService;

    private TextField name =new TextField("Name");
    private TextField eMail = new TextField("E-Mail");
    private PasswordField password1 =new PasswordField("Passwort");
    private PasswordField password2 = new PasswordField("wiederhole Passwort");

    private Button submitRegistration =new Button("Registrieren");




    public RegistrationView(@Autowired AuthService authService) {

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        submitRegistration.addClickListener(e->
                        register(name.getValue(),eMail.getValue(),password1.getValue(),password2.getValue())
               );
        add(
                new H1("Registrierung bei Binäratops"),
                name,eMail,password1,password2,submitRegistration

        );
    }

    private void register(String username, String eMail, String password1, String password2) {
        if(username.trim().isEmpty()){
            Notification.show("geben Sie einen namen ein");
        }else if(password1.isEmpty()){
            Notification.show("geben sie ein Passwort ein");
        }else if(!password1.equals(password2)){
            Notification.show("beide Passwörter müssen gleich sein");
        }else {
            try {
                authService.register(username, password1, eMail);
                UI.getCurrent().getPage().setLocation("login");
            } catch (RegisterException e) {
                Notification.show("der Name ist bereits vergeben");
            }

        }
    }


}
