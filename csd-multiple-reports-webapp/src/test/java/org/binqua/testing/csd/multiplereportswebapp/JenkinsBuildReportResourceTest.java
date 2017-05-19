package org.binqua.testing.csd.multiplereportswebapp;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.binqua.testing.csd.multiplereportswebapp.JenkinsBuildResponse.failed;
import static org.binqua.testing.csd.multiplereportswebapp.JenkinsBuildResponse.successful;

public class JenkinsBuildReportResourceTest {

    private static final int JENKINS_PORT = 8089;
    private static final String JENKINS_BASE_URL = "http://localhost:" + JENKINS_PORT;

    private static final int FIRST_JOB_DURATION = 1111;
    private static final int FIRST_JOB_TIMESTAMP = 1111111;

    private static final int SECOND_JOB_DURATION = FIRST_JOB_DURATION + 1;
    private static final int SECOND_JOB_TIMESTAMP = FIRST_JOB_TIMESTAMP + 1;

    private static final String JENKINS_JOB_NAME = "someJob";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(JENKINS_PORT);

    private final JenkinsBuildReportResource jenkinsBuildReportResource = new JenkinsBuildReportResource();

    @Test
    public void givenAListOfBuildsUrlThenGetJenkinsBuildsDataInvokeJenkinsForEachUrl() throws Exception {

        givenThat(get(urlEqualTo(urlToRetrieveJsonSummaryForJob(141)))
                      .willReturn(aResponse()
                                      .withStatus(200)
                                      .withBody(
                                          "{\"fullDisplayName\":\"fullDisplayName1 #141\",\"number\":141,\"duration\":" + FIRST_JOB_DURATION + ",\"result\":\"FAILURE\",\"timestamp\":"
                                          + FIRST_JOB_TIMESTAMP + ",\"url\":\"http://jenkins.uc/job/a-b-c/141\"}"))
        );

        givenThat(get(urlEqualTo(urlToRetrieveJsonSummaryForJob(142)))
                      .willReturn(aResponse()
                                      .withStatus(200)
                                      .withBody("{\"fullDisplayName\":\"fullDisplayName2 #142\",\"number\":142,\"duration\":" + SECOND_JOB_DURATION + ",\"result\":\"SUCCESS\",\"timestamp\":"
                                                + SECOND_JOB_TIMESTAMP + ",\"url\":\"http://jenkins.uc/job/a-b-c/142\"}"))
        );

        final TestReportInfo firstJenkinsJob = new TestReportInfo(jobUrl(141), "reportDir141", "report 141 pretty name", 1);
        final TestReportInfo secondJenkinsJob = new TestReportInfo(jobUrl(142), "reportDir142", "report 142 pretty name", 100);

        final List<JenkinsBuildResponse> jenkinsResponses = jenkinsBuildReportResource.getJenkinsBuildResponses(new BuildsUrl(asList(firstJenkinsJob, secondJenkinsJob)));

        assertThat(jenkinsResponses, contains(
            successful(new JenkinsBuildSummary("report 141 pretty name", "fullDisplayName1 #141", "reportDir141", 141, FIRST_JOB_DURATION, FIRST_JOB_TIMESTAMP, "FAILURE")),
            successful(new JenkinsBuildSummary("report 142 pretty name", "fullDisplayName2 #142", "reportDir142", 142, SECOND_JOB_DURATION, SECOND_JOB_TIMESTAMP, "SUCCESS"))
        ));

    }

    @Test
    public void givenAResponseExceptionThenResponseIsNonSuccessful() throws Exception {

        final TestReportInfo firstJenkinsJob = new TestReportInfo(jobUrl(142), "cucumber-screenshots-report-first-report-name-build-141", "report 141 pretty name" ,100);

        final List<JenkinsBuildResponse> jenkinsResponses = jenkinsBuildReportResource.getJenkinsBuildResponses(new BuildsUrl(asList(firstJenkinsJob)));

        assertThat(jenkinsResponses, contains(failed(firstJenkinsJob)));

    }

    private String jobUrl(int jobNumber) {
        return JENKINS_BASE_URL + partialJobUrl(jobNumber);
    }

    private String urlToRetrieveJsonSummaryForJob(int jobNumber) {
        return partialJobUrl(jobNumber) + "/api/json?tree=fullDisplayName,number,result,timestamp,duration";
    }

    private String partialJobUrl(int jobNumber) {
        return "/job/" + JENKINS_JOB_NAME + "/" + jobNumber;
    }


}