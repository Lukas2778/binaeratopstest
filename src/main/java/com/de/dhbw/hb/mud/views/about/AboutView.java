package com.de.dhbw.hb.mud.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.de.dhbw.hb.mud.views.main.MainView;

@CssImport("./views/about/about-view.css")
@Route(value = "about", layout = MainView.class)
@PageTitle("About")
public class AboutView extends Div {

    public AboutView() {
        addClassName("about-view");
        add(new Text("Content placeholder"));
    }

}
