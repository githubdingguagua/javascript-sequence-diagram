package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class CentralImageCropperTest {

    @Test
    public void cropDoesSomethingWithoutException() throws Exception {
        assertThat(new CentralImageCropper().crop(createAPngImage()), is(not(nullValue())));
    }

    private byte[] createAPngImage() throws IOException {
        final BufferedImage originalImage = ImageIO.read(ClassLoader.getSystemResource("anImage.png"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

}