package org.binqua.testing.csd.multiplereportswebapp;

import java.util.List;

interface BuildsSummaryWriter {

    void write(List<JenkinsBuildResponse> jenkinsBuildResponses);

}
