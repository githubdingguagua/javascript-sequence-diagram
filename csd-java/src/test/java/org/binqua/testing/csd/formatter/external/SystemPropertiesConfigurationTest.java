package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.common.ManifestReader;
import org.binqua.testing.csd.formatter.report.conversation.DestinationDirectoryFactoryNameDateTimeAppender;
import org.junit.Test;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SystemPropertiesConfigurationTest {

    private final ManifestReader manifestReader = mock(ManifestReader.class);
    private final DestinationDirectoryFactoryNameDateTimeAppender directoryFactoryNameDateTimeAppender = mock(DestinationDirectoryFactoryNameDateTimeAppender.class);

    private Properties systemProperties = new Properties();

    private SystemPropertiesConfiguration underTest = new SystemPropertiesConfiguration(systemProperties,
                                                                                        directoryFactoryNameDateTimeAppender,
                                                                                        manifestReader);

    @Test
    public void configurationReadRightSystemProperties() {

        final String csdBuildUrl = "bla/bla/181/";

        systemProperties.setProperty("csdBuildUrl", csdBuildUrl);

        systemProperties.setProperty("csdBuildPrettyName", "Agent Web");

        systemProperties.setProperty("csdNumberOfBuildsToShow", "5");

        systemProperties.setProperty("generateSequenceDiagram", "enabled");

        assertThat(underTest.buildUrl(), is(csdBuildUrl));

        assertThat(underTest.isGenerateSequenceDiagramEnabled(), is(true));

        assertThat(underTest.isAccessibleFromMultipleReportsPage(), is(true));

        assertThat(underTest.buildPrettyName(), is("Agent Web"));

        assertThat(underTest.numberOfBuildsToShow(), is(5));

    }

    @Test
    public void defaultNumberOfBuildToShowIs10() {
        assertThat(underTest.numberOfBuildsToShow(), is(10));
    }

    @Test
    public void givenNoCsdBuildUrlThenReportIsNotAccessibleFromMultipleReportsPage() {

        systemProperties.setProperty("csdBuildUrl", "");

        assertThat(underTest.isAccessibleFromMultipleReportsPage(), is(false));

    }

    @Test
    public void multipleReportsHomeUrl() {

        final String someValue = "something";
        systemProperties.setProperty("multipleReportsHomeUrl", someValue);
        assertThat(underTest.multipleReportsHomeUrl(), is(someValue));

        systemProperties.setProperty("multipleReportsHomeUrl", "");
        assertThat(underTest.multipleReportsHomeUrl(), is("http://devopstools.uc/jenkins_data/dwp_multiple_reports/index.html"));

    }

    @Test
    public void buildNumberIsCorrect() {

        systemProperties.setProperty("csdBuildUrl", "http://bla-bli/bla/181/");
        assertThat(underTest.buildNumber(), is("181"));

        systemProperties.setProperty("csdBuildUrl", "http://bla-bli-blu/bla/181");
        assertThat(underTest.buildNumber(), is("181"));

    }

    @Test
    public void configurationReadRightSystemPropertiesWithDifferentValues() {

        final String csdBuildUrl = "bliblibli";

        systemProperties.setProperty("csdBuildUrl", csdBuildUrl);

        systemProperties.setProperty("generateSequenceDiagram", "");

        assertThat(underTest.buildUrl(), is(csdBuildUrl));

        assertThat(underTest.isGenerateSequenceDiagramEnabled(), is(false));

        assertThat(underTest.isAccessibleFromMultipleReportsPage(), is(true));

    }

    @Test
    public void givenCsdJavaVersionExistsThenCsdBuildNumberIsCorrect() {

        final String csdBuildNumber = "123";

        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-java")).thenReturn(Optional.of(csdBuildNumber));

        assertThat(underTest.csdBuildNumber(), is(csdBuildNumber));

    }

    @Test
    public void givenCsdJavaVersionIsEmptyThenCsdBuildNumberIsCorrect() {

        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-java")).thenReturn(Optional.empty());

        assertThat(underTest.csdBuildNumber(), is("UNKNOWN"));

    }

    @Test
    public void reportDestinationDirectoryCanBeNotUnique() {
        final String reportDirectoryInSystemProperties = "someDir/andASubDir";

        systemProperties.setProperty("csdReportDir", reportDirectoryInSystemProperties);
        systemProperties.setProperty("csdMakeEveryReportDirUnique", "false");

        File theReportDir = new File(reportDirectoryInSystemProperties);

        assertThat(underTest.reportDestinationDirectory(), is(theReportDir));

    }

    @Test
    public void reportDestinationDirectoryCanBeUnique() {
        final String reportDirectoryInSystemProperties = "someDir/andASubDir";

        systemProperties.setProperty("csdReportDir", reportDirectoryInSystemProperties);
        systemProperties.setProperty("csdMakeEveryReportDirUnique", "true");

        File theReportDir = new File(reportDirectoryInSystemProperties);

        File aUniqueDirectory = new File("theUniqueDir");

        when(directoryFactoryNameDateTimeAppender.createDirectoryNameFrom(theReportDir)).thenReturn(aUniqueDirectory);

        assertThat(underTest.reportDestinationDirectory(), is(aUniqueDirectory));

    }

}