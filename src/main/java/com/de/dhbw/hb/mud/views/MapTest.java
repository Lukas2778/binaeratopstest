package com.de.dhbw.hb.mud.views;

import com.de.dhbw.hb.mud.service.MapService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MapTest extends VerticalLayout {

    @Autowired
    MapService mapService;



    public MapTest() {
        IntegerField i=new IntegerField("DungeonID");
        String name="testbild";
        Div div=new Div();
        div.setHeight(600, Unit.PIXELS);
        div.setWidth(600,Unit.PIXELS);

        //ImgSrc imageSource =new ImgSrc();
        //
        //imageSource.initialize(mapService.createMap(109));
        //    StreamResource resource =
        //            new StreamResource(imageSource, "myimage.png");
        //
        //Image image=new Image("gad", String.valueOf(resource));


        add(i,
              new Button("create thing", e-> {
                BufferedImage img=mapService.createMap(i.getValue());
                  File outputfile = new File("src/main/resources/META-INF/resources/temp"+name+".png");

                  try {
                      ImageIO.write(img, "png", outputfile);
                  } catch (IOException ioException) {
                      ioException.printStackTrace();
                  }
                  Image image=new Image("temp"+name+".png",name);
                    image.setMaxHeight(600,Unit.PIXELS);
                  image.setMaxWidth(600,Unit.PIXELS);
                  div.add(image);
              }),div);


    }
}
