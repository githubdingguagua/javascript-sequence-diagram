package org.binqua.testing.csd.formatter.report.screenshot;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleScreenshotImageWriterTest {

    @Test
    public void writeCreatesAnyMissingDirectoryAndAFileWithRightContent() throws Exception {

        final Path tempDirectory = Files.createTempDirectory("tempDirectory");

        final File someParentDir = new File(tempDirectory.toFile(), "parentDir");
        final File anotherParentDir = new File(someParentDir, "anotherParentDir");
        final File expectedImageFile = new File(anotherParentDir, "1.png");

        final ImageWriter imageWriter = new SimpleScreenshotImageWriter();

        imageWriter.write(expectedImageFile, createABufferedImage());

        assertThatFileExist(expectedImageFile);

    }

    private BufferedImage createABufferedImage() throws IOException {
        return ImageIO.read(ClassLoader.getSystemResource("anImage.png"));
    }

    private void assertThatFileExist(File expectedFile) {
        assertThat("file " + expectedFile + " should exit", expectedFile.exists(), is(true));
    }
}