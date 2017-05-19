package org.binqua.testing.csd.formatter.report.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageWriter {

    void write(File screenshotImageFile, BufferedImage croppedImage);

}
