package org.binqua.testing.csd.formatter.report.screenshot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CentralImageCropper implements ImageCropper {

    @Override
    public BufferedImage crop(byte[] screeshotImage) {
        final BufferedImage bufferedImage = createImageFromBytes(screeshotImage);
        return bufferedImage.getSubimage(900, 50, 1000, bufferedImage.getHeight() - 300);
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            throw new RuntimeException("Problem reading imageData", e);
        }
    }

}


