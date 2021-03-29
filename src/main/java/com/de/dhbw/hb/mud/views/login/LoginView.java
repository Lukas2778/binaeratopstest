package com.de.dhbw.hb.mud.views.login;

import com.de.dhbw.hb.mud.model.UserDto;
import com.de.dhbw.hb.mud.service.registration.AuthService;
import com.de.dhbw.hb.mud.service.registration.exception.AuthException;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@RouteAlias("")
@Route("login")
@PageTitle("login | Binäratops")
public class LoginView extends VerticalLayout {

    LoginForm login = new LoginForm();

    public LoginView(@Autowired AuthService authService) {

        if (VaadinSession.getCurrent().getAttribute(UserDto.class) != null){
            Notification.show("Sie sind bereits angemeldet!");
            UI.getCurrent().navigate("about");
        }

        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        TextField name =new TextField("Benutzername");
        PasswordField passwordField =new PasswordField("Passwort");
        Button logInButton = new Button("Anmelden");



        logInButton.addClickListener(e->{
            try {
                if (VaadinSession.getCurrent().getAttribute(UserDto.class) != null
                && VaadinSession.getCurrent().getAttribute(UserDto.class).getName().equals(name.getValue())){
                    Notification.show("Sie sind bereits angemeldet!");
                }else {
                    authService.authenticate(name.getValue(),passwordField.getValue());
                }
                UI.getCurrent().navigate("about");

            } catch (AuthException authException) {
                Notification.show("Fehler bei der Anmeldung. Prüfen Sie ihre Daten!");
            }

        });

        add(
                new H1("Binäratops Dungeon Anmeldung"),
                new Text("Willkommen zu unserem Projekt."),
                name,
                passwordField,
                logInButton,
                new RouterLink("Registrieren",RegistrationView.class)


        );
    }



}
