package org.binqua.testing.csd.formatter.report.screenshot;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

public class ThumbnailImageWriter implements ImageWriter {

    @Override
    public void write(File screenshotImageFile, BufferedImage croppedImage) {
        createFile(screenshotImageFile);
        try {
            Thumbnails.of(croppedImage)
                .size(250, 250)
                .keepAspectRatio(true)
                .outputFormat("png")
                .toFile(screenshotImageFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void createFile(File screenshotImageFile) {
        createItermediateDirectory(new File(screenshotImageFile.getParent()));
        createNewFile(screenshotImageFile);
    }

    private void createNewFile(File screenshotImageFile) {
        try {
            if (!screenshotImageFile.createNewFile()) {
                throw new IllegalStateException(format("file %s already exist ", screenshotImageFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new IllegalStateException("cannot create file " + screenshotImageFile.getAbsolutePath());
        }
    }

    private void createItermediateDirectory(File dirToBeCreated) {
        dirToBeCreated.mkdirs();
    }
}
