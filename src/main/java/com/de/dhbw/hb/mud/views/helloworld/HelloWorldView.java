package com.de.dhbw.hb.mud.views.helloworld;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;

@CssImport("./views/helloworld/hello-world-view.css")
//@Route(value = "hello", layout = MainView.class)
//@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
public class HelloWorldView extends Div {

    public HelloWorldView() {
        addClassName("hello-world-view");
        add(new Text("Lol"));
    }

}
