package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JenkinsReportBuilderTest {

    private final ReportBuilderConfiguration reportBuilderConfiguration = mock(ReportBuilderConfiguration.class);
    private final ScheduledExecutorService scheduledExecutorService = mock(ScheduledExecutorService.class);
    private final SupportNotifierFactory supportNotifierFactory = mock(SupportNotifierFactory.class);
    private final SupportNotifier supportNotifier = mock(SupportNotifier.class);
    private final ReportRunnable reportRunnable = mock(ReportRunnable.class);

    private JenkinsReportBuilder jenkinsReportBuilder;

    @Before
    public void setUp() throws Exception {

        when(supportNotifierFactory.createNewNotifierFor(JenkinsReportBuilder.class)).thenReturn(supportNotifier);

        jenkinsReportBuilder = new JenkinsReportBuilder(reportBuilderConfiguration, scheduledExecutorService, reportRunnable, supportNotifierFactory);

    }

    @Test
    public void runBuildTheRightReport() throws Exception {

        final int expectedBuildReportRunningInterval = 21;

        when(reportBuilderConfiguration.scanPeriodInSecs()).thenReturn(expectedBuildReportRunningInterval);

        jenkinsReportBuilder.run();

        verify(scheduledExecutorService).scheduleAtFixedRate(reportRunnable, 0, expectedBuildReportRunningInterval, TimeUnit.SECONDS);

    }

    @Test
    public void supportMessagesAreCorrectInCaseSuccessfulScanning() throws Exception {

        when(reportBuilderConfiguration.scanPeriodInSecs()).thenReturn(20);

        jenkinsReportBuilder.run();

        verify(supportNotifier).info("Jenkins Report Builder Job Started");

    }

}