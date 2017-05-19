package org.binqua.testing.csd.multiplereportswebapp;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JenkinsReportBuilder {

    private final ReportBuilderConfiguration reportBuilderConfiguration;
    private final ScheduledExecutorService scheduledExecutorService;
    private final ReportRunnable reportRunnable;
    private final SupportNotifier supportNotifier;

    public JenkinsReportBuilder(ReportBuilderConfiguration reportBuilderConfiguration,
                                ScheduledExecutorService scheduledExecutorService,
                                ReportRunnable reportRunnable,
                                SupportNotifierFactory supportNotifierFactory) {
        this.reportBuilderConfiguration = reportBuilderConfiguration;
        this.scheduledExecutorService = scheduledExecutorService;
        this.reportRunnable = reportRunnable;
        this.supportNotifier = supportNotifierFactory.createNewNotifierFor(JenkinsReportBuilder.class);
    }

    public void run() {
        supportNotifier.info("Jenkins Report Builder Job Started");
        scheduledExecutorService.scheduleAtFixedRate(reportRunnable, 0, reportBuilderConfiguration.scanPeriodInSecs(), TimeUnit.SECONDS);
    }

}
