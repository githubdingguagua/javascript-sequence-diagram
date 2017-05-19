package org.binqua.testing.csd.formatter.report.assets;

import org.apache.commons.io.FileUtils;
import org.binqua.testing.csd.formatter.external.Configuration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildInfoWriterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private Configuration configuration = mock(Configuration.class);

    @Test
    public void writeCreateRightBuildInfoFileWithUrlAndPrettyName() throws Exception {

        final File reportDestinationDir = tempFolder.getRoot();

        final File expectedBuildInfoYmlFile = new File(reportDestinationDir, "buildInfo.yml");

        assertThat(expectedBuildInfoYmlFile.exists(), is(false));

        when(configuration.reportDestinationDirectory()).thenReturn(reportDestinationDir);

        when(configuration.buildUrl()).thenReturn("blabla");

        when(configuration.buildPrettyName()).thenReturn("Agent web");

        when(configuration.numberOfBuildsToShow()).thenReturn(10);


        new BuildInfoWriter(configuration).write();


        assertThat(expectedBuildInfoYmlFile.exists(), is(true));

        assertThat(FileUtils.readFileToString(expectedBuildInfoYmlFile), is("buildUrl : blabla\nbuildPrettyName : Agent web\nnumberOfBuildsToShow : 10"));

    }

}