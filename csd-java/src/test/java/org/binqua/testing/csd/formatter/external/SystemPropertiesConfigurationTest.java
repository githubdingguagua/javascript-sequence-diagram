package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.formatter.report.conversation.DestinationDirectoryFactoryNameDateTimeAppender;
import org.junit.Test;

import org.binqua.testing.csd.common.ManifestReader;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SystemPropertiesConfigurationTest {

    private final UrlConverter htmlReportDir = mock(UrlConverter.class);
    private final ManifestReader manifestReader = mock(ManifestReader.class);
    private final DestinationDirectoryFactoryNameDateTimeAppender directoryFactoryNameDateTimeAppender = mock(DestinationDirectoryFactoryNameDateTimeAppender.class);

    private Properties systemProperties = new Properties();

    private SystemPropertiesConfiguration underTest = new SystemPropertiesConfiguration(systemProperties,
                                                                                        htmlReportDir,
                                                                                        directoryFactoryNameDateTimeAppender,
                                                                                        manifestReader);

    @Test
    public void configurationReadRightSystemProperties() throws Exception {

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
    public void defaultNumberOfBuildToShowIs10() throws Exception {
        assertThat(underTest.numberOfBuildsToShow(), is(10));
    }

    @Test
    public void givenNoCsdBuildUrlThenReportIsNotAccessibleFromMultipleReportsPage() throws Exception {

        systemProperties.setProperty("csdBuildUrl", "");

        assertThat(underTest.isAccessibleFromMultipleReportsPage(), is(false));

    }

    @Test
    public void multipleReportsHomeUrl() throws Exception {

        final String someValue = "something";
        systemProperties.setProperty("multipleReportsHomeUrl", someValue);
        assertThat(underTest.multipleReportsHomeUrl(), is(someValue));

        systemProperties.setProperty("multipleReportsHomeUrl", "");
        assertThat(underTest.multipleReportsHomeUrl(), is("http://devopstools.uc/jenkins_data/dwp_multiple_reports/index.html"));

    }

    @Test
    public void buildNumberIsCorrect() throws Exception {

        systemProperties.setProperty("csdBuildUrl", "http://bla-bli/bla/181/");
        assertThat(underTest.buildNumber(), is("181"));

        systemProperties.setProperty("csdBuildUrl", "http://bla-bli-blu/bla/181");
        assertThat(underTest.buildNumber(), is("181"));

    }

    @Test
    public void configurationReadRightSystemPropertiesWithDifferentValues() throws Exception {

        final String csdBuildUrl = "bliblibli";

        systemProperties.setProperty("csdBuildUrl", csdBuildUrl);

        systemProperties.setProperty("generateSequenceDiagram", "");

        assertThat(underTest.buildUrl(), is(csdBuildUrl));

        assertThat(underTest.isGenerateSequenceDiagramEnabled(), is(false));

        assertThat(underTest.isAccessibleFromMultipleReportsPage(), is(true));

    }

    @Test
    public void givenCsdJavaVersionExistsThenCsdBuildNumberIsCorrect() throws Exception {

        final String csdBuildNumber = "123";

        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-java")).thenReturn(Optional.of(csdBuildNumber));

        assertThat(underTest.csdBuildNumber(), is(csdBuildNumber));

    }

    @Test
    public void givenCsdJavaVersionIsEmptyThenCsdBuildNumberIsCorrect() throws Exception {

        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-java")).thenReturn(Optional.empty());

        assertThat(underTest.csdBuildNumber(), is("UNKNOWN"));

    }

    @Test
    public void reportDestinationDirectoryCanBeNotUnique() throws Exception {
        systemProperties.setProperty("csdMakeEveryReportDirUnique", "false");

        File theReportDir = new File("/bla/bla/bla");

        when(htmlReportDir.file()).thenReturn(theReportDir);

        assertThat(underTest.reportDestinationDirectory(), is(theReportDir));

    }

    @Test
    public void reportDestinationDirectoryCanBeUnique() throws Exception {
        systemProperties.setProperty("csdMakeEveryReportDirUnique", "true");

        File theReportDir = new File("/bla/bla/bla");

        when(htmlReportDir.file()).thenReturn(theReportDir);

        File aUniqueDirectory = new File("theUniqueDir");

        when(directoryFactoryNameDateTimeAppender.createDirectoryNameFrom(theReportDir)).thenReturn(aUniqueDirectory);

        assertThat(underTest.reportDestinationDirectory(), is(aUniqueDirectory));

    }

}