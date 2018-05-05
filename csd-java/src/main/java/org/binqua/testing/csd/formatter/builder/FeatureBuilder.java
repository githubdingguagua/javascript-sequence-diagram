package org.binqua.testing.csd.formatter.builder;

import gherkin.formatter.model.*;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import org.binqua.testing.csd.bridge.external.StepId;

public interface FeatureBuilder {

    void with(Feature feature, String uri);

    void with(Background aBackground);

    void with(Scenario aScenario);

    void withBeforeHook(Match match, Result result);

    void with(Step aStep, StepId stepId);

    void with(Match aMatch);

    void with(Result aResult);

    void with(ScenarioOutline scenarioOutline);

    void withAfterHook(Match match, Result result);

    org.binqua.testing.csd.cucumberreports.model.Feature build();
}
