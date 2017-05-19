package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.util.IdGenerator;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportFileNamesImplTest {

    private static final String FEATURE_ID = "featureId";
    private static final String FEATURE_NAME = "a feature name";
    private static final File DESTINATION_DIR = new File("someDir");

    private final IdGenerator featureIdGenerator = mock(IdGenerator.class);
    private final IdGenerator scenarioIdGenerator = mock(IdGenerator.class);

    private final ReportFileNamesImpl reportFileNames = new ReportFileNamesImpl(DESTINATION_DIR,
                                                                                featureIdGenerator,
                                                                                scenarioIdGenerator);

    @Test
    public void featureReportFileLocationIsCorrect() throws Exception {

        when(featureIdGenerator.idOf(FEATURE_NAME)).thenReturn(FEATURE_ID);

        assertThat(reportFileNames.featureReportFile(FEATURE_NAME),
                is(new File(DESTINATION_DIR, "features" + File.separatorChar +
                                             FEATURE_ID + File.separatorChar +
                                             "featureReport.json")
                )
        );
    }

    @Test
    public void conversationFileLocationIsCorrect() throws Exception {

        when(featureIdGenerator.idOf(FEATURE_NAME)).thenReturn(FEATURE_ID);

        assertThat(reportFileNames.conversationFile(FEATURE_NAME),
                   is(new File(DESTINATION_DIR, "features" + File.separatorChar +
                                                FEATURE_ID + File.separatorChar +
                                                "conversation.json")));
    }

    @Test
    public void featureReportDirFileLocationIsCorrect() throws Exception {

        when(featureIdGenerator.idOf(FEATURE_NAME)).thenReturn(FEATURE_ID);

        assertThat(reportFileNames.featureReportDir(FEATURE_NAME),
                   is(new File(DESTINATION_DIR, "features" + File.separatorChar +
                                                "featureId")));
    }

    @Test
    public void featuresReportDirFileLocationIsCorrect() throws Exception {
        assertThat(reportFileNames.featuresReportDir(),
                   is(new File(DESTINATION_DIR, "features")));
    }

    @Test
    public void reportDirFileLocationIsCorrect() throws Exception {
        assertThat(reportFileNames.reportDirectory(), is(DESTINATION_DIR));
    }

    @Test
    public void screenshotPageSourceFileLocationIsCorrect() throws Exception {

        final String featureCucumberId = "aFeatureId";
        final String featureId = "feature-0";
        when(featureIdGenerator.idOf(featureCucumberId)).thenReturn(featureId);

        final String scenarioCucumberId = "scenarioName";
        final String scenarioId = "scenario-0";
        when(scenarioIdGenerator.idOf(scenarioCucumberId)).thenReturn(scenarioId);

        final File expectedFileName = new File(DESTINATION_DIR, "features" + File.separatorChar +
                                                                featureId + File.separatorChar +
                                                                scenarioId + File.separatorChar +
                                                                "html" + File.separatorChar +
                                                                "1.html");
        assertThat(reportFileNames.screenshotPageSourceFile(featureCucumberId, scenarioCucumberId, "1"), is(expectedFileName));
    }

    @Test
    public void screenshotImageFileLocationIsCorrect() throws Exception {

        final String featureCucumberId = "aFeatureId";
        final String featureId = "feature-0";
        when(featureIdGenerator.idOf(featureCucumberId)).thenReturn(featureId);

        final String scenarioCucumberId = "scenarioName";
        final String scenarioId = "scenario-0";
        when(scenarioIdGenerator.idOf(scenarioCucumberId)).thenReturn(scenarioId);

        final File expectedFileName = new File(DESTINATION_DIR, "features" + File.separatorChar +
                                                                featureId + File.separatorChar +
                                                                scenarioId + File.separatorChar +
                                                                "images" + File.separatorChar +
                                                                "cropped" + File.separatorChar +
                                                                "1.png");
        assertThat(reportFileNames.screenshotImageFile(featureCucumberId, scenarioCucumberId, "1"), is(expectedFileName));
    }

    @Test
    public void screenshotThumbnailImageFileLocationIsCorrect() throws Exception {

        final String featureCucumberId = "aFeatureId";
        final String featureId = "feature-0";
        when(featureIdGenerator.idOf(featureCucumberId)).thenReturn(featureId);

        final String scenarioCucumberId = "scenarioName";
        final String scenarioId = "scenario-0";
        when(scenarioIdGenerator.idOf(scenarioCucumberId)).thenReturn(scenarioId);

        final File expectedFileName = new File(DESTINATION_DIR, "features" + File.separatorChar +
                                                                featureId + File.separatorChar +
                                                                scenarioId + File.separatorChar +
                                                                "images" + File.separatorChar +
                                                                "thumbnails" + File.separatorChar +
                                                                "1.png");
        assertThat(reportFileNames.screenshotThumbnailImageFile(featureCucumberId, scenarioCucumberId, "1"), is(expectedFileName));
    }

    @Test
    public void scenarioScreenshotsFileLocationIsCorrect() throws Exception {

        final String featureCucumberId = "aFeatureId";
        final String featureId = "feature-0";
        when(featureIdGenerator.idOf(featureCucumberId)).thenReturn(featureId);

        final String scenarioCucumberId = "scenarioName";
        final String scenarioId = "scenario-0";
        when(scenarioIdGenerator.idOf(scenarioCucumberId)).thenReturn(scenarioId);

        final File expectedFileName = new File(DESTINATION_DIR,
                                               "features" + File.separatorChar +
                                               featureId + File.separatorChar +
                                               scenarioId + File.separatorChar +
                                               "screenshots.json");
        assertThat(reportFileNames.scenarioScreenshotsFile(featureCucumberId, scenarioCucumberId), is(expectedFileName));
    }
}