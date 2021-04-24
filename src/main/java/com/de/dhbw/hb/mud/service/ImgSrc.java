package com.de.dhbw.hb.mud.service;

//import com.vaadin.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImgSrc /*implements StreamResource.StreamSource*/ {
    ByteArrayOutputStream buffer=null;

    int reloads = 0;

    BufferedImage image;

    // This method generates the stream contents
    public void initialize (BufferedImage image) {

        this.image=image;
}

    //@Override
    public InputStream getStream() {
        try {
            // Write the image to a buffer
            buffer = new ByteArrayOutputStream();
            ImageIO.write(image, "png", buffer);

            // Return a stream from the buffer
            return new ByteArrayInputStream(
                    buffer.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
