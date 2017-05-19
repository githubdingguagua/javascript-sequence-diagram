package org.binqua.testing.csd.multiplereportswebapp;

import java.util.List;

public interface BuildsSummaryFormatter {

    String format(List<JenkinsBuildResponse> jenkinsBuildSummaries);

}
