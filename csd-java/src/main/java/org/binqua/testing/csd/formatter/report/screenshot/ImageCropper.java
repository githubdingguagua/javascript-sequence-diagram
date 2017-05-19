package org.binqua.testing.csd.formatter.report.screenshot;

import java.awt.image.BufferedImage;

public interface ImageCropper {

    BufferedImage crop(byte[] screeshotImage);

}
