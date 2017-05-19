package org.binqua.testing.csd.formatter.report.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static java.lang.String.format;

public class SimpleScreenshotImageWriter implements ImageWriter {

    @Override
    public void write(File screenshotImageFile, BufferedImage croppedImage) {
        createFile(screenshotImageFile);
        try {
            ImageIO.write(croppedImage, "png", screenshotImageFile);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot crop file " + screenshotImageFile, e);
        }
    }

    private void createFile(File screenshotImageFile) {
        final File dirToBeCreated = new File(screenshotImageFile.getParent());
        dirToBeCreated.mkdirs();
        try {
            if (!screenshotImageFile.createNewFile()) {
                throw new IllegalStateException(format("file %s already exist ", screenshotImageFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new IllegalStateException("cannot create file " + screenshotImageFile.getAbsolutePath());
        }
    }

}


