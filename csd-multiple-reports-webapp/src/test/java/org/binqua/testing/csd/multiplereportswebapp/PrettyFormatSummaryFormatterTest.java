package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrettyFormatSummaryFormatterTest {

    @Test
    public void prettyFormatWorks() throws Exception {

        final List<JenkinsBuildResponse> jenkinsBuildResponseList = new ArrayList<>();
        final BuildsSummaryFormatter buildFormatSummaryFormatter = mock(BuildsSummaryFormatter.class);

        when(buildFormatSummaryFormatter.format(jenkinsBuildResponseList)).thenReturn("[{\"k\":\"v\"}]");

        assertThat(new PrettyFormatSummaryFormatter(buildFormatSummaryFormatter).format(jenkinsBuildResponseList), is("[\n  {\n    \"k\": \"v\"\n  }\n]"));

    }
}
