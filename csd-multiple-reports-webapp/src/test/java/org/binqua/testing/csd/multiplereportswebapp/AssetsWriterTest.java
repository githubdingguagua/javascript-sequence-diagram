package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;

public class AssetsWriterTest {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    private File directoryToCreateTheReportInto ;

    @Test
    public void allFilesAreCopied() throws Exception {

        directoryToCreateTheReportInto = new File(tempFolder.getRoot(), "directoryToCreateTheReportInto");

        new AssetsWriter(directoryToCreateTheReportInto).write();

        assertThatExist(css("jquery-ui.min"));
        assertThatExist(css("jquery-ui.structure.min"));
        assertThatExist(css("style"));
        assertThatExist(css("w2ui-1.4.3"));

        assertThatExist(file("index.html"));

        assertThatExist(javascript("jquery-2.1.4"));
        assertThatExist(javascript("jquery-ui-1.11.4.min"));
        assertThatExist(javascript("w2ui-1.4.3"));

    }

    private File javascript(String fileName) {
        return file("/" + fileName + ".js");
    }

    private File css(String fileName) {
        return file("/css/" + fileName + ".css");
    }

    private void assertThatExist(File actualFile) {
        assertThat("file " + actualFile + " should exist", actualFile.exists());
    }

    private File file(String fileLocationSuffix) {
        return new File(directoryToCreateTheReportInto.getAbsolutePath(), fileLocationSuffix);
    }

}