package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JenkinsBuildSummaryTest {

    @Test
    public void jobNameIsCorrect() throws Exception {
        assertThat(summaryWithFullName("a-b-c #1").name(), is("a-b-c"));
        assertThat(summaryWithFullName("a-b-c    #1").name(), is("a-b-c"));
    }

    @Test
    public void jobIdIsCorrect() throws Exception {
        assertThat(summaryWithFullName("a-b-c #1").id(), is("a-b-c-1"));
    }

    @Test
    public void longTextIsCorrect() throws Exception {
        assertThat(new JenkinsBuildSummary("", "a-b-c #1", "aDir", 1, 5588192, 1469394242132L, "").longText(), is("#1 Sun 24-Jul-2016 @ 22:04:02 took 01:33:08"));
    }

    private JenkinsBuildSummary summaryWithFullName(String fullDisplayName) {
        return new JenkinsBuildSummary("", fullDisplayName, "aDir", 1, 1, 1, "");
    }

}