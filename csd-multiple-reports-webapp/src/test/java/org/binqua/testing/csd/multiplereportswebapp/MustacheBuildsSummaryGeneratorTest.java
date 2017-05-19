package org.binqua.testing.csd.multiplereportswebapp;

import com.google.common.base.Charsets;

import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MustacheBuildsSummaryGeneratorTest {

    private static final String CSD_BUILD_NUMBER = "123";
    private static final int SCAN_PERIOD_IN_SECS = 20;
    private static final java.lang.String CSD_HOME_PAGE_URL = "http://csdHomePage";

    private static final String BUILD_START_DATE_TIME = "Thur 23 May 2016 at 14:12:00";

    private static final String NEXT_BUILD_START_DATE_TIME = "Thur 23 May 2016 at 14:12:20";

    private static final JenkinsBuildResponse FIRST_BUILD_RESPONSE = mock(JenkinsBuildResponse.class);
    private static final JenkinsBuildResponse SECOND_BUILD_RESPONSE = mock(JenkinsBuildResponse.class);

    private static final List<JenkinsBuildResponse> JENKINS_BUILD_RESPONSES = asList(FIRST_BUILD_RESPONSE, SECOND_BUILD_RESPONSE);

    private final BuildsSummaryFormatter buildsSummaryFormatter = mock(BuildsSummaryFormatter.class);
    private final ReportBuilderConfiguration reportBuilderConfiguration = mock(ReportBuilderConfiguration.class);
    private final DateTimeFormatter dateTimeFormatter = mock(DateTimeFormatter.class);
    private final DateTimeGenerator dateTimeGenerator = mock(DateTimeGenerator.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private File buildsSummaryFileDirectory;

    @Test
    public void shouldBeAbleToGenerateAReportFromAModel() throws IOException {

        buildsSummaryFileDirectory = tempFolder.getRoot();

        when(reportBuilderConfiguration.reportDirectoryRoot()).thenReturn(buildsSummaryFileDirectory);
        when(reportBuilderConfiguration.scanPeriodInSecs()).thenReturn(SCAN_PERIOD_IN_SECS);
        when(reportBuilderConfiguration.csdBuildNumber()).thenReturn(CSD_BUILD_NUMBER);
        when(reportBuilderConfiguration.csdHomePageUrl()).thenReturn(CSD_HOME_PAGE_URL);

        final DateTime startBuildDateTime = new DateTime();
        when(dateTimeGenerator.now()).thenReturn(startBuildDateTime);
        when(dateTimeFormatter.format(startBuildDateTime)).thenReturn(BUILD_START_DATE_TIME);
        when(dateTimeFormatter.format(startBuildDateTime.plusSeconds(SCAN_PERIOD_IN_SECS))).thenReturn(NEXT_BUILD_START_DATE_TIME);

        when(buildsSummaryFormatter.format(JENKINS_BUILD_RESPONSES)).thenReturn("[{ \"key\" : \"<span>some text</span>\" }]");

        new MustacheBuildsSummaryGenerator(buildsSummaryFormatter, reportBuilderConfiguration, dateTimeFormatter, dateTimeGenerator).write(JENKINS_BUILD_RESPONSES);

        File actualBuildsSummaryFile = new File(buildsSummaryFileDirectory, "buildsSummary.js");

        assertThatFileExist(actualBuildsSummaryFile);

        assertThat(asString(actualBuildsSummaryFile), is(asString("expected-buildsSummary.js")));
    }

    private void assertThatFileExist(File actualBuildsSummaryFileContent) {
        assertThat("file " + actualBuildsSummaryFileContent.getAbsolutePath() + " should exist", actualBuildsSummaryFileContent.exists(), is(true));
    }

    private String asString(File actualBuildsSummaryFile) throws IOException {
        return Files.lines(actualBuildsSummaryFile.toPath(), Charsets.UTF_8).collect(Collectors.joining("\n"));
    }

    @SuppressWarnings("ConstantConditions")
    private String asString(String name) {
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(name).toURI());

            return Files.lines(path, Charsets.UTF_8).collect(Collectors.joining("\n"));
        } catch (URISyntaxException |IOException|NullPointerException e) {
            throw new IllegalStateException("Failed to find resource " + name, e);
        }
    }

}