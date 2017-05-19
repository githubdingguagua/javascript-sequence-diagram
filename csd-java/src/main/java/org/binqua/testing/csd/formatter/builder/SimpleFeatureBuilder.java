package org.binqua.testing.csd.formatter.builder;

import com.google.common.collect.Lists;
import gherkin.formatter.model.*;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import org.binqua.testing.csd.formatter.util.IdGenerator;
import org.bniqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.cucumberreports.model.Argument;
import org.binqua.testing.csd.cucumberreports.model.Status;

import java.util.List;
import java.util.stream.Collectors;

class SimpleFeatureBuilder implements FeatureBuilder {

    private IdGenerator scenarioIdGenerator;
    private IdGenerator featureIdGenerator;
    private ToDataRowTransformer toDataRowTransformer;

    public SimpleFeatureBuilder(IdGenerator scenarioIdGenerator, IdGenerator featureIdGenerator, ToDataRowTransformer toDataRowTransformer) {
        this.scenarioIdGenerator = scenarioIdGenerator;
        this.featureIdGenerator = featureIdGenerator;
        this.toDataRowTransformer = toDataRowTransformer;
    }

    private enum Phase {step, match, embedding, output, result;}

    private org.binqua.testing.csd.cucumberreports.model.Feature feature;

    @Override
    public void with(Feature feature, String uri) {
        this.feature = new org.binqua.testing.csd.cucumberreports.model.Feature(
                featureIdGenerator.idOf(feature.getId()),
                convertTags(feature.getTags()),
                feature.getDescription(),
                feature.getName(),
                feature.getKeyword(),
                feature.getLine(),
                Lists.newArrayList(),
                uri
        );
    }

    @Override
    public void with(Background aBackground) {
        feature.getScenarios().add(new org.binqua.testing.csd.cucumberreports.model.Scenario(
                scenarioIdGenerator.backgroundId(),
                aBackground.getDescription(),
                aBackground.getName(),
                aBackground.getKeyword(),
                aBackground.getLine(),
                Lists.newArrayList(),
                "background",
                Lists.newArrayList()
        ));
    }

    @Override
    public void with(Scenario aScenario) {
        feature.getScenarios().add(new org.binqua.testing.csd.cucumberreports.model.Scenario(
                scenarioIdGenerator.idOf(aScenario.getId()),
                aScenario.getDescription(),
                aScenario.getName(),
                aScenario.getKeyword(),
                aScenario.getLine(),
                Lists.newArrayList(),
                "scenario",
                convertTags(aScenario.getTags())
        ));
    }

    @Override
    public void withBeforeHook(Match match, Result result) {
    }

    @Override
    public void with(Step aStep, StepId stepId) {
        steps().add(new org.binqua.testing.csd.cucumberreports.model.Step(
                stepId.asString(),
                null,
                aStep.getLine(),
                aStep.getKeyword(),
                aStep.getName(),
                null,
                Lists.newArrayList(),
                toDataRowTransformer.toDataRow(aStep.getRows())
        ));
    }

    @Override
    public void with(Match aMatch) {
        getCurrentStep(Phase.match).setMatch(new org.binqua.testing.csd.cucumberreports.model.Match(
                aMatch.getLocation(),
                convertArguments(aMatch.getArguments())
        ));
    }

    @Override
    public void with(Result aResult) {
        getCurrentStep(Phase.result).setResult(new org.binqua.testing.csd.cucumberreports.model.Result(
                aResult.getDuration(),
                Status.withStatusString(aResult.getStatus()),
                aResult.getErrorMessage()
        ));
    }

    @Override
    public void with(ScenarioOutline scenarioOutline) {
    }

    @Override
    public void withAfterHook(Match match, Result result) {
    }

    @Override
    public org.binqua.testing.csd.cucumberreports.model.Feature build() {
        return feature;
    }

    private org.binqua.testing.csd.cucumberreports.model.Step getCurrentStep(Phase phase) {
        for (org.binqua.testing.csd.cucumberreports.model.Step step : steps()) {
            if (((step.getResult() == null) && (phase == Phase.result)) ||
                    ((step.getMatch() == null) && (phase == Phase.match))) {
                return step;
            }
        }

        return null;
    }

    private List<org.binqua.testing.csd.cucumberreports.model.Step> steps() {
        return latestScenario().getSteps();
    }

    private org.binqua.testing.csd.cucumberreports.model.Scenario latestScenario() {
        return feature.getScenarios().get(feature.getScenarios().size() - 1);
    }

    private List<org.binqua.testing.csd.cucumberreports.model.Tag> convertTags(List<gherkin.formatter.model.Tag> tags) {
        return tags.stream().map(t -> new org.binqua.testing.csd.cucumberreports.model.Tag(t.getName(), t.getLine())).collect(Collectors.toList());
    }

    private List<Argument> convertArguments(List<gherkin.formatter.Argument> arguments) {
        return arguments.stream().map(a -> new Argument(a.getVal(), a.getOffset())).collect(Collectors.toList());
    }
}
