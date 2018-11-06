package org.binqua.testing.csd.report;

import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.bridge.external.ConversationSupport;
import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.builder.JGivenFeatureBuilder;
import org.binqua.testing.csd.formatter.builder.JGivenFeatureBuilderFactory;
import org.binqua.testing.csd.formatter.report.assets.AssetsWriter;
import org.binqua.testing.csd.formatter.report.conversation.*;
import org.binqua.testing.csd.formatter.report.featuremenu.FeatureMenuWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeatures;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeaturesFactory;
import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;
import org.binqua.testing.csd.formatter.util.IdGenerator;

public class JGivenTestObserver implements TestObserver {

    private final TestFeatures testFeatures;
    private AssetsWriter assetsWriter;
    private FeatureMenuWriter featureMenuWriter;
    private JGivenFeatureBuilderFactory jGivenFeatureBuilderFactory;
    private IdGenerator scenarioIdGenerator;
    private IdGenerator featureIdGenerator;
    private FeatureConversationFactory featureConversationFactory;
    private ConversationSupport conversationSupport;
    private DecoratedConversationFactory decoratedConversationFactory;
    private ScenarioReportFactory scenarioReportFactory;
    private FeatureReportWriter featureReportWriter;
    private FeatureIndexWriter featureIndexWriter;
    private FeatureConversation featureConversation;
    private Scenario scenario;
    private JGivenFeatureBuilder aFeatureBuilder;

    public JGivenTestObserver(IdGenerator scenarioIdGenerator,
                              IdGenerator featureIdGenerator,
                              FeatureConversationFactory featureConversationFactory,
                              ConversationSupport conversationSupport,
                              DecoratedConversationFactory decoratedConversationFactory,
                              ScenarioReportFactory scenarioReportFactory,
                              FeatureReportWriter featureReportWriter,
                              FeatureIndexWriter featureIndexWriter,
                              TestFeaturesFactory testFeaturesFactory,
                              AssetsWriter assetsWriter,
                              FeatureMenuWriter featureMenuWriter,
                              JGivenFeatureBuilderFactory jGivenFeatureBuilderFactory) {
        this.scenarioIdGenerator = scenarioIdGenerator;
        this.featureIdGenerator = featureIdGenerator;
        this.featureConversationFactory = featureConversationFactory;
        this.conversationSupport = conversationSupport;
        this.decoratedConversationFactory = decoratedConversationFactory;
        this.scenarioReportFactory = scenarioReportFactory;
        this.featureReportWriter = featureReportWriter;
        this.featureIndexWriter = featureIndexWriter;
        this.testFeatures = testFeaturesFactory.createTestFeatures();
        this.assetsWriter = assetsWriter;
        this.featureMenuWriter = featureMenuWriter;
        this.jGivenFeatureBuilderFactory = jGivenFeatureBuilderFactory;
    }

    @Override
    public void notifyFeatureExecutionStarted(String featureId) {
        featureIdGenerator.record(featureId.toLowerCase());
        featureConversation = featureConversationFactory.createAFeatureConversation();
        aFeatureBuilder = jGivenFeatureBuilderFactory.createAFeatureBuilder();
        aFeatureBuilder.withFeature(featureId.toLowerCase());
    }

    @Override
    public void notifyScenarioExecutionStarted(String scenarioId) {
        final String internalScenarioId = scenarioIdGenerator.record(scenarioId.toLowerCase());
        scenario = new Scenario(internalScenarioId, internalScenarioId);
        aFeatureBuilder.withScenario(scenarioId.toLowerCase());
    }

    @Override
    public void notifyScenarioExecutionEnded(String cucumberFeatureId, String scenarioId) {
        final Conversation conversation = conversationSupport.conversation();
        final DecoratedConversation decoratedConversation = decoratedConversationFactory.decorate(conversation);
        featureConversation.add(scenarioReportFactory.createAScenarioReport(scenario, new ScenarioScreenshots(), decoratedConversation));
        conversationSupport.clearConversation();
    }

    @Override
    public void notifyFeatureExecutionEnded(String cucumberFeatureId) {
        featureReportWriter.write(cucumberFeatureId.toLowerCase(), featureConversation);
        final Feature feature = aFeatureBuilder.build();
        featureIndexWriter.write(cucumberFeatureId.toLowerCase(), feature);
        testFeatures.add(feature);
    }

    @Override
    public void notifyTestEnded() {
        assetsWriter.write();
        featureMenuWriter.write(testFeatures);
    }

    @Override
    public void notifyStepExecutionStarted(StepContext stepContext) {

    }

    @Override
    public void notifyStepExecutionEnded(StepContext stepContext) {

    }
}
