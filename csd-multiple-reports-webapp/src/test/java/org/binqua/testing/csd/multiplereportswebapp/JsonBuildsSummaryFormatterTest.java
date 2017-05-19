package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;
import org.mockito.Mockito;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class JsonBuildsSummaryFormatterTest {

    private static final String JENKINS_JOB_URL_PREFIX = "http://jenkinsurl/something/job/";

    private ReportBuilderConfiguration reportBuilderConfiguration = Mockito.mock(ReportBuilderConfiguration.class);

    @Test
    public void buildsAreSortedByJobNameAndAfterBuildNumber() throws Exception {

        when(reportBuilderConfiguration.reportHomePageUrlPattern()).thenReturn("http://something/$/index.html");

        final BuildsSummaryFormatter buildsSummaryFormatter = new JsonBuildsSummaryFormatter(reportBuilderConfiguration);

        JenkinsBuildResponse firstJenkinsBuildSummary = JenkinsBuildResponse.successful(new JenkinsBuildSummary("job c", "job-c #102", "report-c-102", 102, 10000, 1469127248030L, "SUCCESS"));

        JenkinsBuildResponse fourthJenkinsBuildSummary = JenkinsBuildResponse.successful(new JenkinsBuildSummary("job a", "job-a #102", "report-a-102", 102, 10000, 1469127248030L, "FAILURE"));

        JenkinsBuildResponse secondJenkinsBuildSummary = JenkinsBuildResponse.successful(new JenkinsBuildSummary("job c", "job-c #101", "report-c-101", 101, 10000, 1469127248030L, "FAILURE"));

        JenkinsBuildResponse fifthJenkinsBuildSummary = JenkinsBuildResponse.successful(new JenkinsBuildSummary("job b", "job-b #106", "report-b-106", 106, 10000, 1469127248030L, "FAILURE"));

        JenkinsBuildResponse thirdJenkinsBuildSummary = JenkinsBuildResponse.successful(new JenkinsBuildSummary("job c", "job-c #103", "report-c-103", 103, 10000, 1469127248030L, "SUCCESS"));

        JenkinsBuildResponse jenkinsIsDownBuildSummary = JenkinsBuildResponse.failed(new TestReportInfo(JENKINS_JOB_URL_PREFIX + "job-d/110", "report-d-110", "job d", 1));

        final String actualFormattedReport = buildsSummaryFormatter.format(asList(firstJenkinsBuildSummary,
                                                                                  secondJenkinsBuildSummary,
                                                                                  thirdJenkinsBuildSummary,
                                                                                  fourthJenkinsBuildSummary,
                                                                                  fifthJenkinsBuildSummary,
                                                                                  jenkinsIsDownBuildSummary));

        final String expectedContent = "["
                                       + "{'id':'job-a','text':'job a','img':'icon-folder','nodes':[{'id':'job-a-102','text':'<span class=\\\"testFailed\\\">#102 Thu 21-Jul-2016 @ 19:54:08 took 00:00:10</span>','url':'http://something/report-a-102/index.html'}]},"
                                       + "{'id':'job-b','text':'job b','img':'icon-folder','nodes':[{'id':'job-b-106','text':'<span class=\\\"testFailed\\\">#106 Thu 21-Jul-2016 @ 19:54:08 took 00:00:10</span>','url':'http://something/report-b-106/index.html'}]},"
                                       + "{'id':'job-c','text':'job c','img':'icon-folder','nodes':["
                                       + "{'id':'job-c-103','text':'<span class=\\\"testPassed\\\">#103 Thu 21-Jul-2016 @ 19:54:08 took 00:00:10</span>','url':'http://something/report-c-103/index.html'},"
                                       + "{'id':'job-c-102','text':'<span class=\\\"testPassed\\\">#102 Thu 21-Jul-2016 @ 19:54:08 took 00:00:10</span>','url':'http://something/report-c-102/index.html'},"
                                       + "{'id':'job-c-101','text':'<span class=\\\"testFailed\\\">#101 Thu 21-Jul-2016 @ 19:54:08 took 00:00:10</span>','url':'http://something/report-c-101/index.html'}"
                                       + "]},"
                                       + "{'id':'job-d','text':'job d','img':'icon-folder','nodes':[{'id':'job-d-110','text':'<span class=\\\"serverProblems\\\">#110 It looks like jenkins had problems</span>','url':'"
                                       + JENKINS_JOB_URL_PREFIX + "job-d/110'}]}" +
                                       "]";

        assertThat(actualFormattedReport, is(replaceSingleWithDoubleQuotes(expectedContent)));

    }


    private String replaceSingleWithDoubleQuotes(String expectedContent) {
        return expectedContent.replaceAll("'", "\"");
    }
}