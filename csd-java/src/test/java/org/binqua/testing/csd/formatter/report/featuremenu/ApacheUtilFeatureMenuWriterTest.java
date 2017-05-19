package org.binqua.testing.csd.formatter.report.featuremenu;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.conversation.ToJson;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApacheUtilFeatureMenuWriterTest {

    private final FeatureMenuContentGenerator featureMenuContentGenerator = mock(FeatureMenuContentGenerator.class);
    private final ReportFileNames reportFileNames = mock(ReportFileNames.class);
    private final ToJson toJson = mock(ToJson.class);

    @Test
    public void writeCreateTheTestsMenuFileWithTheRightContent() throws Exception {

        Path dirParentOfReportDir = Files.createTempDirectory("dirParentOfReportDir");

        final String expectedContent = "some content";
        when(featureMenuContentGenerator.content(toJson)).thenReturn(expectedContent);

        final File reportDir = new File(dirParentOfReportDir.toFile(), "reportDir");
        when(reportFileNames.reportDirectory()).thenReturn(reportDir);

        final ApacheUtilFeatureMenuWriter featureMenuWriter = new ApacheUtilFeatureMenuWriter(featureMenuContentGenerator, reportFileNames);

        featureMenuWriter.write(toJson);

        final File expectedTestMenuFile = new File(reportDir, "testsMenu.js");

        final String actualContent = FileUtils.readFileToString(expectedTestMenuFile);

        assertThat(expectedTestMenuFile.getAbsolutePath() + " content is wrong", actualContent, is(expectedContent));

    }

}