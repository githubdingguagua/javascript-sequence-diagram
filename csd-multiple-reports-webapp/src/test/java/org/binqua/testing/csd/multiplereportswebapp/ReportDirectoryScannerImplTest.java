package org.binqua.testing.csd.multiplereportswebapp;


import org.apache.commons.io.FileUtils;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentMatcher;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReportDirectoryScannerImplTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private final SupportNotifierFactory supportNotifierFactory = mock(SupportNotifierFactory.class);
    private final SupportNotifier supportNotifier = mock(SupportNotifier.class);

    @Before
    public void setUp() throws Exception {
        when(supportNotifierFactory.createNewNotifierFor(ReportDirectoryScannerImpl.class)).thenReturn(supportNotifier);
    }

    @Test
    public void givenAReportsDirectoryWith2BuildInfoYmlFilesThenScanExtractAllBuildsUrl() throws Exception {

        final File reportRooToBeScanned = dirToAnalyse();

        final ReportsDirBuilder reportsDirBuilder = new ReportsDirBuilder(reportRooToBeScanned);

        final File report1Dir = reportsDirBuilder.createADir("cucumber-screenshots-report-first-report-name-build-142");
        reportsDirBuilder.createAFile(new File(report1Dir, "buildInfo.yml"), "buildUrl: \"http://jenkins.uc/job/a-b-c/142\"\nbuildPrettyName: a pretty name for 142\nnumberOfBuildsToShow: 1");
        reportsDirBuilder.createAFile(new File(report1Dir, "anotherFile"), "blabla");

        final File report2Dir = reportsDirBuilder.createADir("cucumber-screenshots-report-first-report-name-build-141");
        reportsDirBuilder.createAFile(new File(report2Dir, "buildInfo.yml"), "buildUrl: \"http://jenkins.uc/job/a-b-c/141\"\nbuildPrettyName: a pretty name for 141\nnumberOfBuildsToShow: 2");
        reportsDirBuilder.createAFile(new File(report2Dir, "anotherFile"), "blabla");

        reportsDirBuilder.createADir("aNonReportDir");

        assertThat(buildInstanceUnderTest(supportNotifierFactory, dirToAnalyse()).scan().testReportInfoList(),
                   is(asList(
                       new TestReportInfo("http://jenkins.uc/job/a-b-c/141", "cucumber-screenshots-report-first-report-name-build-141", "a pretty name for 141", 2),
                       new TestReportInfo("http://jenkins.uc/job/a-b-c/142", "cucumber-screenshots-report-first-report-name-build-142", "a pretty name for 142", 1)
                   )));

        verify(supportNotifier).info("Start scanning dir " + dirToAnalyse().getAbsolutePath());

        verify(supportNotifier).info(format("Found file %s/cucumber-screenshots-report-first-report-name-build-141/buildInfo.yml to be parsed", dirToAnalyse()));
        verify(supportNotifier).info(format("File parsed successful and found buildUrl: http://jenkins.uc/job/a-b-c/141 " +
                                            "reportDirectory: cucumber-screenshots-report-first-report-name-build-141 " +
                                            "buildPrettyName: a pretty name for 141"));

        verify(supportNotifier).info(format("Found file %s/cucumber-screenshots-report-first-report-name-build-142/buildInfo.yml to be parsed", dirToAnalyse()));
        verify(supportNotifier).info(format("File parsed successful and found buildUrl: http://jenkins.uc/job/a-b-c/142 " +
                                            "reportDirectory: cucumber-screenshots-report-first-report-name-build-142 " +
                                            "buildPrettyName: a pretty name for 142"));

    }

    @Test
    public void givenAReportsDirectoryWithNoBuildInfoYmlFileThenScanExtractAllBuildsUrl() throws Exception {

        final ReportsDirBuilder reportsDirBuilder = new ReportsDirBuilder(dirToAnalyse());

        final File report1Dir = reportsDirBuilder.createADir("cucumber-screenshots-report-first-report-name-build-141");
        reportsDirBuilder.createAFile(new File(report1Dir, "buildInfo.yml"), "buildUrl: \"http://jenkins.uc/job/a-b-c/141\"\nbuildPrettyName: a pretty name for 141\nnumberOfBuildsToShow: 10");
        reportsDirBuilder.createAFile(new File(report1Dir, "anotherFile"), "blabla");

        final File reportDirWithoutBuildInfoYmlFile = reportsDirBuilder.createADir("cucumber-screenshots-report-first-report-name-build-1235");
        reportsDirBuilder.createAFile(new File(reportDirWithoutBuildInfoYmlFile, "anotherFile"), "blabla");

        reportsDirBuilder.createADir("aNonReportDir");

        assertThat(buildInstanceUnderTest(supportNotifierFactory, dirToAnalyse()).scan().testReportInfoList(),
                   is(asList(new TestReportInfo("http://jenkins.uc/job/a-b-c/141", "cucumber-screenshots-report-first-report-name-build-141", "a pretty name for 141", 10))));

    }

    @Test
    public void givenAReportsDirectoryWithACrapBuildInfoYmlFileThenRightMessageIsNotifiedAndScanDoesNotThrowAnException() throws Exception {

        final ReportsDirBuilder reportsDirBuilder = new ReportsDirBuilder(dirToAnalyse());

        final File reportDir = reportsDirBuilder.createADir("cucumber-screenshots-report-first-report-name-build-141");
        reportsDirBuilder.createAFile(new File(reportDir, "buildInfo.yml"), "is not a valid yml file");

        assertThat(buildInstanceUnderTest(supportNotifierFactory, dirToAnalyse()).scan().testReportInfoList(), is(emptyList()));

        final String expectedMessage = "Problem parsing " + new File(dirToAnalyse(), "cucumber-screenshots-report-first-report-name-build-141/buildInfo.yml");

        verify(supportNotifier).info(eq(expectedMessage), exceptionMatcher("java.lang.String cannot be cast to java.util.Map"));

    }

    static Exception exceptionMatcher(String expected) {
        return argThat(new ExceptionMatcher(expected));
    }

    static class ExceptionMatcher extends ArgumentMatcher<Exception> {

        private final String expectedMessage;

        public ExceptionMatcher(String expectedMessage) {
            this.expectedMessage = expectedMessage;
        }

        @Override
        public boolean matches(Object actual) {
            return ((Exception) actual).getMessage().equals(expectedMessage);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(expectedMessage == null ? null : expectedMessage);
        }
    }

    private ReportDirectoryScannerImpl buildInstanceUnderTest(SupportNotifierFactory support, File dirContainingAllReports) {
        return new ReportDirectoryScannerImpl(support, "cucumber-screenshots-report-.*-\\d+", dirContainingAllReports);
    }

    private File dirToAnalyse() {
        return tempFolder.getRoot();
    }

    class ReportsDirBuilder {

        private File tempFolder;

        ReportsDirBuilder(File tempFolder) {
            this.tempFolder = tempFolder;
        }

        File createADir(String dirToBeCreated) {
            final File reportDir = new File(tempFolder, dirToBeCreated);
            reportDir.mkdir();
            return reportDir;
        }

        void createAFile(File fileToBeCreated, String fileContent) throws IOException {
            FileUtils.writeStringToFile(fileToBeCreated, fileContent);
        }
    }
}
