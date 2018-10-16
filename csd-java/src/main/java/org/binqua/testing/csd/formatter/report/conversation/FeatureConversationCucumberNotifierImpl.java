package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.bridge.external.ConversationSupport;
import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.bridge.external.StepContextObserver;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.external.Configuration;
import org.binqua.testing.csd.formatter.report.assets.AssetsWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.FeatureMenuWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeatures;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeaturesFactory;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;
import org.binqua.testing.csd.formatter.report.screenshot.Screenshot;
import org.binqua.testing.csd.formatter.report.screenshot.ScreenshotNotifier;
import org.binqua.testing.csd.formatter.report.screenshot.ScreenshotWriter;
import org.binqua.testing.csd.formatter.util.IdGenerator;

public class FeatureConversationCucumberNotifierImpl implements CucumberNotifier, ScreenshotNotifier {

    private DecoratedConversationFactory decoratedConversationFactory;
    private FeatureReportWriter featureReportWriter;
    private FeatureIndexWriter featureIndexWriter;
    private FeatureMenuWriter featureMenuWriter;
    private ScreenshotWriter screenshotWriter;
    private AssetsWriter assetsWriter;
    private ConversationSupport conversationSupport;
    private StepContextObserver stepContextObserver;
    private IdGenerator scenarioIdGenerator;
    private IdGenerator featureIdGenerator;
    private ScenarioReportFactory scenarioReportFactory;
    private FeatureConversationFactory featureConversationFactory;
    private Scenario scenario;
    private FeatureConversation featureConversation;
    private TestFeatures testFeatures;
    private ScenarioScreenshots scenarioScreenshots;
    private ScenarioScreenshotsWriter scenarioScreenshotsWriter;
    private Configuration configuration;

    public FeatureConversationCucumberNotifierImpl(DecoratedConversationFactory decoratedConversationFactory,
                                                   FeatureReportWriter featureReportWriter,
                                                   FeatureIndexWriter featureIndexWriter,
                                                   ScenarioScreenshotsWriter scenarioScreenshotsWriter,
                                                   FeatureMenuWriter featureMenuWriter,
                                                   AssetsWriter assetsWriter,
                                                   ScreenshotWriter screenshotWriter,
                                                   ConversationSupport conversationSupport,
                                                   StepContextObserver stepContextObserver,
                                                   IdGenerator scenarioIdGenerator,
                                                   IdGenerator featureIdGenerator,
                                                   ScenarioReportFactory scenarioReportFactory,
                                                   FeatureConversationFactory featureConversationFactory,
                                                   TestFeaturesFactory testFeaturesFactory,
                                                   Configuration configuration) {
        this.decoratedConversationFactory = decoratedConversationFactory;
        this.featureReportWriter = featureReportWriter;
        this.featureIndexWriter = featureIndexWriter;
        this.featureMenuWriter = featureMenuWriter;
        this.screenshotWriter = screenshotWriter;
        this.assetsWriter = assetsWriter;
        this.conversationSupport = conversationSupport;
        this.stepContextObserver = stepContextObserver;
        this.scenarioIdGenerator = scenarioIdGenerator;
        this.featureIdGenerator = featureIdGenerator;
        this.scenarioReportFactory = scenarioReportFactory;
        this.featureConversationFactory = featureConversationFactory;
        this.scenarioScreenshotsWriter = scenarioScreenshotsWriter;
        this.testFeatures = testFeaturesFactory.createTestFeatures();
        this.configuration = configuration;
    }

    @Override
    public void notifyFeatureExecutionStarted(String featureId) {
        featureIdGenerator.record(featureId);
        featureConversation = featureConversationFactory.createAFeatureConversation();
    }

    @Override
    public void notifyBackgroundStarted() {
        scenarioScreenshots = new ScenarioScreenshots();
    }

    @Override
    public void notifyStepExecutionStarted(StepContext stepContext) {
        scenarioScreenshots.notifyStepContextStart(stepContext);
    }

    @Override
    public void notifyStepExecutionEnded(StepContext stepContext) {
        stepContextObserver.notifyStepContextEnd(stepContext);
        scenarioScreenshots.notifyStepContextEnd(stepContext);
    }

    @Override
    public void notifyScenarioExecutionStarted(String cucumberScenarioId) {
        if (backgroundIsNotPresent()) {
            scenarioScreenshots = new ScenarioScreenshots();
        }
        final String scenarioId = scenarioIdGenerator.record(cucumberScenarioId);
        scenario = new Scenario(scenarioId, scenarioId);
    }

    private boolean backgroundIsNotPresent() {
        return scenarioScreenshots == null;
    }

    @Override
    public void notifyScreenshot(Screenshot screenshot) {
        if (screenshot.isNotValid()) {
            return;
        }
        scenarioIdGenerator.record(screenshot.scenarioId());
        screenshotWriter.write(scenarioScreenshots.add(screenshot));
    }

    @Override
    public void notifyScenarioExecutionEnded(String cucumberFeatureId, String cucumberScenarioId) {
        final Conversation conversation = conversationSupport.conversation();
        final DecoratedConversation decoratedConversation = decoratedConversationFactory.decorate(conversation);
        featureConversation.add(scenarioReportFactory.createAScenarioReport(scenario, scenarioScreenshots, decoratedConversation));
        scenarioScreenshotsWriter.write(cucumberFeatureId, cucumberScenarioId, scenarioScreenshots);
        conversationSupport.clearConversation();
        scenarioScreenshots = null;
    }

    @Override
    public void notifyFeatureExecutionEnded(String cucumberFeatureId, Feature feature) {
        featureReportWriter.write(cucumberFeatureId, featureConversation);
        featureIndexWriter.write(cucumberFeatureId, feature);
        testFeatures.add(feature);
    }

    @Override
    public void notifyTestEnded() {
        assetsWriter.write();
        featureMenuWriter.write(testFeatures);
    }

}
