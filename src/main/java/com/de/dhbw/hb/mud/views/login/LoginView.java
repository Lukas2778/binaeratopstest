package com.de.dhbw.hb.mud.views.login;

import com.de.dhbw.hb.mud.service.registration.exception.AuthException;
import com.de.dhbw.hb.mud.service.registration.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;


@Route("login")
@PageTitle("login | Binäratops")
public class LoginView extends VerticalLayout {

    private AuthService authService;

    LoginForm login = new LoginForm();

    public LoginView(@Autowired AuthService authService) {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        TextField name =new TextField("Benutzername");
        PasswordField passwordField =new PasswordField("Passwort");
        Button logInButton = new Button("Anmelden");



        logInButton.addClickListener(e->{
            try {
                authService.authenticate(name.getValue(),passwordField.getValue());
                UI.getCurrent().navigate("about");
            } catch (AuthException authException) {
                Notification.show("fehler");
            }

        });

        add(
                new H1("Binäratops dungeon login"),
                name,
                passwordField,
                logInButton,
                new RouterLink("Registrieren",RegistrationView.class)


        );
    }



}
