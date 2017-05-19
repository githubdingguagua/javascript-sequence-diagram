package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;
import org.mockito.Mockito;

import org.binqua.testing.csd.common.ManifestReader;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class OptionsReportBuilderConfigurationTest {

    private final ManifestReader manifestReader = Mockito.mock(ManifestReader.class);

    private final OptionsReportBuilderConfiguration configurationWithMandatoryParametersOnly = new OptionsReportBuilderConfiguration(manifestReader, new String[]{
        "-directoryToScan", "abc",
        "-reportDir", "def"
    });

    @Test
    public void configurationIsCorrect() throws Exception {

        final OptionsReportBuilderConfiguration configuration = new OptionsReportBuilderConfiguration(manifestReader, new String[]{
            "-directoryToScan", "abc",
            "-reportDir", "def",
            "-scanPeriodInSecs", "100",
            "-reportDirectoryRegexMatcher", "regex",
            "-reportHomePageUrlPattern", "aDirPattern",
            });

        assertThat(configuration.directoryToScan(), is(new File("abc")));

        assertThat(configuration.reportDirectoryRoot(), is(new File("def")));

        assertThat(configuration.scanPeriodInSecs(), is(100));

        assertThat(configuration.reportDirectoryRegexMatcher(), is("regex"));

        assertThat(configuration.reportHomePageUrlPattern(), is("aDirPattern"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void givenWrongConfigurationThenExceptionIsThrown() throws Exception {
        new OptionsReportBuilderConfiguration(null, new String[]{});
    }

    @Test
    public void givenNoPeriodInSecsThenDefaultValueIs10Secs() throws Exception {
        assertThat(configurationWithMandatoryParametersOnly.scanPeriodInSecs(), is(10));
    }

    @Test
    public void givenNoSingleReportDirectoryRegexMatcherThenDefaultValueIsCorrect() throws Exception {
        assertThat(configurationWithMandatoryParametersOnly.reportDirectoryRegexMatcher(), is("cucumber-screenshots-report-.*-\\d+"));
    }

    @Test
    public void givenNoReportHomePageUrlPatternThenDefaultValueIsCorrect() throws Exception {
        assertThat(configurationWithMandatoryParametersOnly.reportHomePageUrlPattern(), is("http://devopstools.uc/jenkins_data/$/index.html"));
    }

    @Test
    public void givenNoCsdHomePageUrlThenDefaultValueIsCorrect() throws Exception {
        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-multiple-reports-webapp")).thenReturn(Optional.of("123"));

        assertThat(configurationWithMandatoryParametersOnly.csdHomePageUrl(), is("http://jenkins.uc/job/cucumber-sequence-diagram/123"));
        assertThat(configurationWithMandatoryParametersOnly.csdBuildNumber(), is("123"));

    }

    @Test
    public void csdBuildNumberIsUnknownIfIsNotDefined() throws Exception {
        when(manifestReader.buildNumberOfManifestContainingAttributeValue("csd-multiple-reports-webapp")).thenReturn(Optional.empty());

        assertThat(configurationWithMandatoryParametersOnly.csdBuildNumber(), is("UNKNOWN"));
        assertThat(configurationWithMandatoryParametersOnly.csdHomePageUrl(), is("http://jenkins.uc/job/cucumber-sequence-diagram"));
    }

}