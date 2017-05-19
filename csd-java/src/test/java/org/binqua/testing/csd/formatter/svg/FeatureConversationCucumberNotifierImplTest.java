package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.formatter.report.assets.AssetsWriter;
import org.junit.Test;

import org.bniqua.testing.csd.bridge.external.ConversationSupport;
import org.bniqua.testing.csd.bridge.external.StepContextObserver;
import org.binqua.testing.csd.formatter.external.Configuration;
import org.binqua.testing.csd.formatter.report.conversation.DecoratedConversationFactory;
import org.binqua.testing.csd.formatter.report.conversation.FeatureConversationCucumberNotifierImpl;
import org.binqua.testing.csd.formatter.report.conversation.FeatureConversationFactory;
import org.binqua.testing.csd.formatter.report.conversation.FeatureIndexWriter;
import org.binqua.testing.csd.formatter.report.conversation.FeatureReportWriter;
import org.binqua.testing.csd.formatter.report.conversation.ScenarioReportFactory;
import org.binqua.testing.csd.formatter.report.conversation.ScenarioScreenshotsWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.FeatureMenuWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeaturesFactory;
import org.binqua.testing.csd.formatter.report.screenshot.ScreenshotWriter;
import org.binqua.testing.csd.formatter.util.IdGenerator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FeatureConversationCucumberNotifierImplTest {

    private final FeatureReportWriter featureReportWriter = mock(FeatureReportWriter.class);
    private final ConversationSupport conversationSupport = mock(ConversationSupport.class);
    private final IdGenerator scenarioIdGenerator = mock(IdGenerator.class);
    private final IdGenerator featureIdGenerator = mock(IdGenerator.class);
    private final DecoratedConversationFactory decoratedConversationFactory = mock(DecoratedConversationFactory.class);
    private final ScenarioReportFactory scenarioReportFactory = mock(ScenarioReportFactory.class);
    private final FeatureConversationFactory featureConversationFactory = mock(FeatureConversationFactory.class);
    private final FeatureIndexWriter featureIndexWriter = mock(FeatureIndexWriter.class);
    private final FeatureMenuWriter featureMenuWriter = mock(FeatureMenuWriter.class);
    private final AssetsWriter assetsWriter = mock(AssetsWriter.class);
    private final ScreenshotWriter screenshotWriter = mock(ScreenshotWriter.class);
    private final ScenarioScreenshotsWriter scenarioScreenshotsWriter = mock(ScenarioScreenshotsWriter.class);
    private final TestFeaturesFactory featuresFactory = mock(TestFeaturesFactory.class);
    private final StepContextObserver stepContextObserver = mock(StepContextObserver.class);
    private final Configuration configuration = mock(Configuration.class);

    private final FeatureConversationCucumberNotifierImpl featureConversationBuilder = new FeatureConversationCucumberNotifierImpl(
            decoratedConversationFactory,
            featureReportWriter,
            featureIndexWriter,
            scenarioScreenshotsWriter,
            featureMenuWriter,
            assetsWriter,
            screenshotWriter,
            conversationSupport,
            stepContextObserver,
            scenarioIdGenerator,
            featureIdGenerator,
            scenarioReportFactory,
            featureConversationFactory,
            featuresFactory,
            configuration
    );

    @Test
    public void notifyFeatureExecutionStartedRecordTheFeatureName() throws Exception {

        final String featureName = "a feature name";

        featureConversationBuilder.notifyFeatureExecutionStarted(featureName);

        verify(featureIdGenerator).record(featureName);

    }

    @Test
    public void notifyScenarioExecutionStartedRecordTheScenarioName() throws Exception {

        final String scenarioName = "a scenario name";

        featureConversationBuilder.notifyScenarioExecutionStarted(scenarioName);

        verify(scenarioIdGenerator).record(scenarioName);

    }
}