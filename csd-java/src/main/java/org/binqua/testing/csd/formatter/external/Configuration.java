package org.binqua.testing.csd.formatter.external;

import java.io.File;
import java.util.Map;

public interface Configuration {

    String buildUrl();

    String buildPrettyName();

    boolean isGenerateSequenceDiagramEnabled();

    boolean isAccessibleFromMultipleReportsPage();

    File reportDestinationDirectory();

    Map<String,Integer> clusterNamePortMap();

    String buildNumber();

    String multipleReportsHomeUrl();

    String csdBuildNumber();

    int numberOfBuildsToShow();

    boolean makeEveryReportDirUnique();
}
