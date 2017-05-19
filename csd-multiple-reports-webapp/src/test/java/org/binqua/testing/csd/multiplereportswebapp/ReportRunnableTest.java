package org.binqua.testing.csd.multiplereportswebapp;

import org.junit.Test;
import org.mockito.InOrder;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportRunnableTest {


    private static final int SCAN_PERIOD_IN_SECS = 32;

    private static final String CSD_BUILD_VERSION = "123";

    private final ReportDirectoryScanner reportDirectoryScanner = mock(ReportDirectoryScanner.class);

    private final JenkinsBuildReportResource jenkinsBuildReportResource = mock(JenkinsBuildReportResource.class);

    private final BuildsSummaryWriter buildsSummaryWriter = mock(BuildsSummaryWriter.class);

    private final SupportNotifierFactory supportNotifierFactory = mock(SupportNotifierFactory.class);

    private final SupportNotifier supportNotifier = mock(SupportNotifier.class);

    private final JenkinsBuildResponse jenkinsBuildResponse = mock(JenkinsBuildResponse.class);

    private final AssetsWriter assetsWriter = mock(AssetsWriter.class);

    @Test
    public void runWritesTheReportAfterScanningTheDirectoryAndCallingJenkins() throws Exception {

        final BuildsUrl buildsUrl = mock(BuildsUrl.class);
        when(reportDirectoryScanner.scan()).thenReturn(buildsUrl);

        final List<JenkinsBuildResponse> jenkinsBuildResponses = Collections.singletonList(jenkinsBuildResponse);
        when(jenkinsBuildReportResource.getJenkinsBuildResponses(buildsUrl)).thenReturn(jenkinsBuildResponses);

        when(supportNotifierFactory.createNewNotifierFor(ReportRunnable.class)).thenReturn(supportNotifier);

        final ReportRunnable reportRunnable = new ReportRunnable(reportDirectoryScanner,
                                                                 jenkinsBuildReportResource,
                                                                 buildsSummaryWriter,
                                                                 assetsWriter,
                                                                 supportNotifierFactory,
                                                                 SCAN_PERIOD_IN_SECS,
                                                                 CSD_BUILD_VERSION);

        reportRunnable.run();

        InOrder order = inOrder(assetsWriter, buildsSummaryWriter, supportNotifier);

        order.verify(supportNotifier).info("Starting building report using csd version 123");

        order.verify(assetsWriter).write();

        order.verify(buildsSummaryWriter).write(jenkinsBuildResponses);

        order.verify(supportNotifier).info("Report built successfully. Next build will take place in 32 secs");

    }

    @Test
    public void supportMessagesAreCorrectInCaseOfBuildCreateAnException() throws Exception {

        when(supportNotifierFactory.createNewNotifierFor(ReportRunnable.class)).thenReturn(supportNotifier);

        final RuntimeException toBeThrown = new RuntimeException("an exception");

        doThrow(toBeThrown).when(assetsWriter).write();

        final ReportRunnable reportRunnable = new ReportRunnable(reportDirectoryScanner,
                                                                 jenkinsBuildReportResource,
                                                                 buildsSummaryWriter,
                                                                 assetsWriter,
                                                                 supportNotifierFactory,
                                                                 SCAN_PERIOD_IN_SECS,
                                                                 CSD_BUILD_VERSION);

        reportRunnable.run();

        InOrder order = inOrder(assetsWriter, supportNotifier);

        order.verify(supportNotifier).info("Starting building report using csd version 123");

        order.verify(supportNotifier).info("Report build failed.", toBeThrown);
    }
}