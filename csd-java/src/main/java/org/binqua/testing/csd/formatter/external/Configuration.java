package org.binqua.testing.csd.formatter.external;

import java.io.File;

public interface Configuration {

    String buildUrl();

    String buildPrettyName();

    boolean isGenerateSequenceDiagramEnabled();

    boolean isAccessibleFromMultipleReportsPage();

    File reportDestinationDirectory();

    String buildNumber();

    String multipleReportsHomeUrl();

    String csdBuildNumber();

    int numberOfBuildsToShow();

    boolean makeEveryReportDirUnique();
}
