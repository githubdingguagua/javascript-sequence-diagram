package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestReportInfoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void givenABuildThatEndsWithSlashThenNameAndNumberAreCorrect() throws Exception {

        final TestReportInfo testReportInfo = new TestReportInfo("http://jenkins.uc/job/a-b-c/142/", "partOfDirectoryReport", "a prettyName", 2);

        assertThat(testReportInfo.name(), is("a-b-c"));
        assertThat(testReportInfo.number(), is(142));
        assertThat(testReportInfo.prettyName(), is("a prettyName"));
        assertThat(testReportInfo.numberOfBuildsToShow(), is(2));

    }

    @Test
    public void numberOfBuildsToShowCannotBe0() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("numberOfBuildsToShow cannot be 0");

        new TestReportInfo("http://jenkins.uc/job/a-b-c/142/", "partOfDirectoryReport", "a prettyName", 0);
    }

    @Test
    public void givenABuildThatEndsWithSlashAndContainsJobInItsNameThenNameAndNumberAreCorrect() throws Exception {

        final TestReportInfo testReportInfo = new TestReportInfo("http://jenkins.uc/job/job-a-b-c/143/", "partOfDirectoryReport", "a prettyName", 3);

        assertThat(testReportInfo.name(), is("job-a-b-c"));
        assertThat(testReportInfo.number(), is(143));
        assertThat(testReportInfo.prettyName(), is("a prettyName"));
        assertThat(testReportInfo.numberOfBuildsToShow(), is(3));

    }

    @Test
    public void givenABuildThatEndsWithoutSlashThenNameAndNumberAreCorrect() throws Exception {

        final TestReportInfo testReportInfo = new TestReportInfo("http://jenkins.uc/job/a-b-c/142", "partOfDirectoryReport", "a prettyName", 1);

        assertThat(testReportInfo.name(), is("a-b-c"));
        assertThat(testReportInfo.number(), is(142));
        assertThat(testReportInfo.prettyName(), is("a prettyName"));

    }

}