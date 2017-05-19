package org.binqua.testing.csd.multiplereportswebapp;

import java.io.File;

interface ReportBuilderConfiguration {

    File directoryToScan();

    File reportDirectoryRoot();

    int scanPeriodInSecs();

    String reportDirectoryRegexMatcher();

    String reportHomePageUrlPattern();

    String csdBuildNumber();

    String csdHomePageUrl();
}
