package org.binqua.testing.csd.formatter.report.conversation;

import org.apache.commons.io.FileUtils;
import org.binqua.testing.csd.formatter.report.screenshot.ApachePageSourceWriter;
import org.binqua.testing.csd.formatter.report.screenshot.PageSourceWriter;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ApachePageSourceWriterTest {

    @Test
    public void writeCreatesAnyMissingDirectoryAndAFileWithRightContent() throws Exception {

        final Path tempDirectory = Files.createTempDirectory("tempDirectory");

        final String expectedPageSourceContent = "some content";

        final File someParentDir = new File(tempDirectory.toFile(), "parentDir");
        final File anotherParentDir = new File(someParentDir, "anotherParentDir");
        final File expectedScreenshotPageSourceFile = new File(anotherParentDir, "1.org.binqua.testing.csd.multiplereportwebapp.html");

        final PageSourceWriter pageSourceWriter = new ApachePageSourceWriter();

        pageSourceWriter.write(expectedScreenshotPageSourceFile, expectedPageSourceContent);

        assertThatFileExist(expectedScreenshotPageSourceFile);

        assertThat(FileUtils.readFileToString(expectedScreenshotPageSourceFile), is(expectedPageSourceContent));

    }

    private void assertThatFileExist(File expectedScreenshotPageSourceFile) {
        assertThat("file " + expectedScreenshotPageSourceFile + " should exit", expectedScreenshotPageSourceFile.exists(), is(true));
    }
}