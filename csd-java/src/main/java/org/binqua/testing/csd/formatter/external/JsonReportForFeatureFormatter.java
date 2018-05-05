package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.formatter.builder.FeatureBuilderFactory;
import org.binqua.testing.csd.formatter.report.conversation.CucumberNotifier;
import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.formatter.builder.FeatureBuilder;
import org.binqua.testing.csd.formatter.builder.SimpleFeatureBuilderFactory;
import org.binqua.testing.csd.formatter.report.conversation.CucumberNotifierFactory;

import java.net.URL;
import java.util.List;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

public class JsonReportForFeatureFormatter implements Reporter, Formatter {

    private CucumberNotifier cucumberNotifier;
    private ConversationContextsFactory conversationContextsFactory;

    private String uri;
    private Feature featureUnderExecution;
    private Scenario scenarioUnderExecution;
    private StepContexts stepContexts;
    private FeatureBuilderFactory featureBuilderFactory;
    private FeatureBuilder featureBuilder;

    public JsonReportForFeatureFormatter(URL htmlReportDir, FeatureBuilderFactory featureBuilderFactory) {
        this.featureBuilderFactory = featureBuilderFactory;
    }

    public JsonReportForFeatureFormatter(URL htmlReportDir) {
        this.cucumberNotifier = CucumberNotifierFactory.instance(new ConfigurationFactory(htmlReportDir, System.getProperties()).createConfiguration());
        this.featureBuilderFactory = new SimpleFeatureBuilderFactory();
        this.conversationContextsFactory = new SimpleConversationContextsFactory();
    }

    @Override
    public void uri(String uri) {
        this.uri = uri;
    }

    @Override
    public void feature(Feature featureJustStartedButNotFinishedYet) {
        if (thereIsAlreadyAScenarioUnderExecution()) {
            notifyScenarioUnderExecutionEnded();
        }
        if (thereIsAlreadyAFeatureUnderExecution()) {
            notifyFeatureUnderExecutionEnded();
        }
        featureUnderExecution = featureJustStartedButNotFinishedYet;
        cucumberNotifier.notifyFeatureExecutionStarted(featureUnderExecution.getId());

        featureBuilder = featureBuilderFactory.createAFeatureBuilder();
        featureBuilder.with(featureJustStartedButNotFinishedYet, uri);
    }

    @Override
    public void background(Background background) {
        if (thereIsAlreadyAScenarioUnderExecution()) {
            notifyScenarioUnderExecutionEnded();
        }
        cucumberNotifier.notifyBackgroundStarted();
        this.stepContexts = conversationContextsFactory.createConversationContexts();
        featureBuilder.with(background);
    }

    @Override
    public void scenario(Scenario scenarioJustStartedButNotFinishedYet) {
        if (thereIsAlreadyAScenarioUnderExecution()) {
            notifyScenarioUnderExecutionEnded();
        }
        cucumberNotifier.notifyScenarioExecutionStarted(scenarioJustStartedButNotFinishedYet.getId());
        this.stepContexts = conversationContextsFactory.createConversationContexts();

        this.scenarioUnderExecution = scenarioJustStartedButNotFinishedYet;
        featureBuilder.with(scenarioJustStartedButNotFinishedYet);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        featureBuilder.with(scenarioOutline);
    }

    @Override
    public void examples(Examples examples) {
    }

    @Override
    public void step(Step step) {
        featureBuilder.with(step, stepContexts.recordStep(step.getName()));
    }

    @Override
    public void match(Match match) {
        final StepContext stepContext = stepContexts.recordStepMatch();
        featureBuilder.with(match);
        cucumberNotifier.notifyStepExecutionStarted(stepContext);
    }

    @Override
    public void result(Result result) {
        featureBuilder.with(result);
        cucumberNotifier.notifyStepExecutionEnded(stepContexts.lastStepContextMatched());
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {

    }

    private boolean thereIsAlreadyAScenarioUnderExecution() {
        return scenarioUnderExecution != null;
    }

    @Override
    public void before(Match match, Result result) {
        featureBuilder.withBeforeHook(match, result);
    }

    @Override
    public void after(Match match, Result result) {
        featureBuilder.withAfterHook(match, result);
    }

    @Override
    public void eof() {
    }

    private void notifyFeatureUnderExecutionEnded() {
        this.cucumberNotifier.notifyFeatureExecutionEnded(featureUnderExecution.getId(), featureBuilder.build());
        this.featureUnderExecution = null;
    }

    private void notifyScenarioUnderExecutionEnded() {
        cucumberNotifier.notifyScenarioExecutionEnded(featureUnderExecution.getId(), scenarioUnderExecution.getId());
        scenarioUnderExecution = null;
    }

    private boolean thereIsAlreadyAFeatureUnderExecution() {
        return featureUnderExecution != null;
    }

    @Override
    public void done() {
    }

    @Override
    public void close() {
        if (thereIsAlreadyAScenarioUnderExecution()) {
            notifyScenarioUnderExecutionEnded();
        }
        cucumberNotifier.notifyFeatureExecutionEnded(featureUnderExecution.getId(), featureBuilder.build());
        cucumberNotifier.notifyTestEnded();
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        // NoOp
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        // NoOp
    }

}

