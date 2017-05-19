package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FilteredByNumberOfBuildsReportDirectoryScannerDecoratorTest {

    private final ReportDirectoryScanner reportDirectoryScanner = mock(ReportDirectoryScanner.class);

    @Test
    public void testReportAreFilteredByNumberOfBuildsToShowAndHigherBuildNumber() throws Exception {

        final List<TestReportInfo> originalTestReportList = asList(
            aTestReportInfo("141", "report a", 2),
            aTestReportInfo("150", "report b", 1),
            aTestReportInfo("145", "report a", 2),
            aTestReportInfo("153", "report b", 1),
            aTestReportInfo("142", "report a", 2),
            aTestReportInfo("160", "report b", 1),
            aTestReportInfo("170", "report c", 3),
            aTestReportInfo("171", "report c", 3),
            aTestReportInfo("172", "report c", 3),
            aTestReportInfo("100", "report c", 3),
            aTestReportInfo("101", "report c", 3),
            aTestReportInfo("50", "report d", 10)
        );

        when(reportDirectoryScanner.scan()).thenReturn(new BuildsUrl(originalTestReportList));

        final List<TestReportInfo> TestReportList = new FilteredByNumberOfBuildsReportDirectoryScannerDecorator(reportDirectoryScanner).scan().testReportInfoList();

        assertThat(TestReportList, is(asList(aTestReportInfo("50", "report d", 10),
                                             aTestReportInfo("172", "report c", 3),
                                             aTestReportInfo("171", "report c", 3),
                                             aTestReportInfo("170", "report c", 3),
                                             aTestReportInfo("160", "report b", 1),
                                             aTestReportInfo("145", "report a", 2),
                                             aTestReportInfo("142", "report a", 2)

        )));
    }

    private TestReportInfo aTestReportInfo(String buildUrl, String prettyName, int numberOfBuildsToShow) {
        return new TestReportInfo("http://weDonCareEvenOfThis/job/a-b-c/" + buildUrl, "weDontCare", prettyName, numberOfBuildsToShow);
    }
}