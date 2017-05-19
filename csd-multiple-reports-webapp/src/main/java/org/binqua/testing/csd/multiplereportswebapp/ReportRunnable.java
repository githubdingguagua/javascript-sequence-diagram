package org.binqua.testing.csd.multiplereportswebapp;

import static java.lang.String.format;

class ReportRunnable implements Runnable {

    private final ReportDirectoryScanner reportDirectoryScanner;
    private final JenkinsBuildReportResource jenkinsBuildReportResource;
    private final BuildsSummaryWriter buildsSummaryWriter;
    private final AssetsWriter assetsWriter;
    private final SupportNotifier supportNotifier;
    private String csdBuildNumber;
    private final int scanPeriodInSecs;

    ReportRunnable(ReportDirectoryScanner reportDirectoryScanner,
                   JenkinsBuildReportResource jenkinsBuildReportResource,
                   BuildsSummaryWriter buildsSummaryWriter,
                   AssetsWriter assetsWriter,
                   SupportNotifierFactory supportNotifierFactory,
                   int scanPeriodInSecs,
                   String csdBuildNumber) {
        this.reportDirectoryScanner = reportDirectoryScanner;
        this.jenkinsBuildReportResource = jenkinsBuildReportResource;
        this.buildsSummaryWriter = buildsSummaryWriter;
        this.assetsWriter = assetsWriter;
        this.scanPeriodInSecs = scanPeriodInSecs;
        this.supportNotifier = supportNotifierFactory.createNewNotifierFor(ReportRunnable.class);
        this.csdBuildNumber = csdBuildNumber;
    }

    public void run() {
        supportNotifier.info(startingReportGenerationMessage(csdBuildNumber));
        try {
            assetsWriter.write();
            buildsSummaryWriter.write(jenkinsBuildReportResource.getJenkinsBuildResponses(reportDirectoryScanner.scan()));
            supportNotifier.info(reportGenerationFinishedMessage());
        } catch (Exception e) {
            supportNotifier.info(reportGenerationThrownAnExceptionMessage(), e);
        }
    }

    private String startingReportGenerationMessage(String csdBuildNumber) {
        return format("Starting building report using csd version %s", csdBuildNumber);
    }

    private String reportGenerationFinishedMessage() {
        return format("Report built successfully. Next build will take place in %s secs", scanPeriodInSecs);
    }

    private String reportGenerationThrownAnExceptionMessage() {
        return "Report build failed.";
    }

}
