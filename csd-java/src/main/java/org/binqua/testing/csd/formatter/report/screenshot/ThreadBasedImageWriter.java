package org.binqua.testing.csd.formatter.report.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Executor;

public class ThreadBasedImageWriter implements ImageWriter {

    private Executor executor;
    private ImageWriter imageWriter;

    public ThreadBasedImageWriter(Executor executor, ImageWriter imageWriter) {
        this.executor = executor;
        this.imageWriter = imageWriter;
    }

    @Override
    public void write(File screenshotImageFile, BufferedImage croppedImage) {
        executor.execute(() -> imageWriter.write(screenshotImageFile, croppedImage));
    }

}



