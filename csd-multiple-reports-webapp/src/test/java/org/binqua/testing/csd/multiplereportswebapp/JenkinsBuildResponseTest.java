package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.binqua.testing.csd.multiplereportswebapp.JenkinsBuildResponse.failed;
import static org.binqua.testing.csd.multiplereportswebapp.JenkinsBuildResponse.successful;

public class JenkinsBuildResponseTest {

    @Test
    public void givenAFailedBuildThenNameAndNumberAreCorrect() throws Exception {

        final JenkinsBuildResponse failedUnderTest = failed(new TestReportInfo("http://jenkins.uc/job/a-b-c/142/", "aDirName", "a pretty name", 2));

        assertThat(failedUnderTest.name(), is("a-b-c"));
        assertThat(failedUnderTest.number(), is(142));
        assertThat(failedUnderTest.isSuccessful(), is(false));
        assertThat(failedUnderTest.jobPrettyName(), is("a pretty name"));

    }

    @Test
    public void givenAFailedBuildThatEndWithoutSlashThenNameAndNumberAreCorrect() throws Exception {

        final JenkinsBuildResponse failedUnderTest = failed(new TestReportInfo("http://jenkins.uc/job/a-b-c/142", "aDirName", "a pretty name", 1));

        assertThat(failedUnderTest.name(), is("a-b-c"));
        assertThat(failedUnderTest.number(), is(142));
        assertThat(failedUnderTest.jobPrettyName(), is("a pretty name"));

    }

    @Test
    public void aSuccessfulResponseIsSuccessful() throws Exception {
        assertThat(successful(new JenkinsBuildSummary("", "a  123", "aDir", 123, 1234L, 1234L, "SUCCESS")).isSuccessful(), is(true));
    }

}