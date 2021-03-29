package com.de.dhbw.hb.mud.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@CssImport("./views/about/about-view.css")
//@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class AboutView extends VerticalLayout {

    public AboutView() {
        Div div=new Div();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidth("60%");
        addClassName("about-view");

        div.add(new H1("Willkommen bei Binäratops"),
                new Text("Sie haben sich bei dem Prototyp unseres Multi-User-Dungeon (MUD) registriert. Das Ziel" +
                        " dieses Projekts ist es eine Plattform zum Erstellen und Bespielen von personalisierten" +
                        " MUDs zu schaffen. Hier können einige Funktionen ausprobiert werden. Zum Beispiel kann ein" +
                        " Dungeon erstellt werden oder man kann sich im öffentlichen Chat mit anderen" +
                        " Dungeon-Enthusiasten unterhalten. \n" +
                        "Wir wünschen viel Spaß beim Umschauen!\n"));
        add(div);
    }

}
