package com.de.dhbw.hb.mud.views;

import com.de.dhbw.hb.mud.model.TestClass;
import com.de.dhbw.hb.mud.repository.TestClassRepository;
import com.de.dhbw.hb.mud.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "test", layout = MainView.class)
@PageTitle("Test")
public class TestView extends VerticalLayout {

    @Autowired
    private TestClassRepository repo;

    private TextField name=new TextField();
    private TextField race=new TextField();
    private IntegerField age = new IntegerField();
    private IntegerField size = new IntegerField();
    private Button commit =new Button("Senden");

    public TestView(TestClassRepository repo){
        this.repo = repo;


        commit.addClickListener(e->{
            repo.save(new TestClass(name.getValue(),race.getValue(),age.getValue(),size.getValue()));
            name.clear();
            race.clear();
            age.clear();
            size.clear();
            name.focus();
        });


        add(new H1("Hallo"),
                new HorizontalLayout(new Text("Name:"),name),
                new HorizontalLayout(new Text("Rasse:"),race),
                new HorizontalLayout(new Text("Alter:"),age),
                new HorizontalLayout(new Text("Größe:"),size),
                commit);


    }

}
