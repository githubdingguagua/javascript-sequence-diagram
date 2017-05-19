package org.binqua.testing.csd.formatter.report.screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class NoImageCropper implements ImageCropper {

    @Override
    public BufferedImage crop(byte[] screenshotImage) {
        return createImageFromBytes(screenshotImage);
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        try {
            return ImageIO.read(new BufferedInputStream(new ByteArrayInputStream(imageData), imageData.length));
        } catch (IOException e) {
            throw new RuntimeException("Problem reading imageData", e);
        }
    }

}


