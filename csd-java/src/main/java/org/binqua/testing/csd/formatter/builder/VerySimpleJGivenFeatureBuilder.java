package org.binqua.testing.csd.formatter.builder;

import com.google.common.collect.Lists;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.util.IdGenerator;

import java.util.Collections;

public class VerySimpleJGivenFeatureBuilder implements JGivenFeatureBuilder {

    private IdGenerator featureIdGenerator;
    private IdGenerator scenarioIdGenerator;
    private Feature feature;

    public VerySimpleJGivenFeatureBuilder(IdGenerator featureIdGenerator,IdGenerator scenarioIdGenerator) {
        this.featureIdGenerator = featureIdGenerator;
        this.scenarioIdGenerator = scenarioIdGenerator;
    }

    @Override
    public void withFeature(String featureId) {
        this.feature = new org.binqua.testing.csd.cucumberreports.model.Feature(
                featureIdGenerator.idOf(featureId),
                Collections.emptyList(),
                "description",
                featureIdGenerator.idOf(featureId),
                "Feature",
                0,
                Lists.newArrayList(),
                ""
        );
    }

    @Override
    public void withScenario(String scenarioId) {
        feature.getScenarios().add(new org.binqua.testing.csd.cucumberreports.model.Scenario(
                scenarioIdGenerator.idOf(scenarioId),
                "description",
                scenarioId,
                "Scenario",
                0,
                Lists.newArrayList(),
                "scenario",
                Collections.EMPTY_LIST
        ));
    }

    @Override
    public org.binqua.testing.csd.cucumberreports.model.Feature build() {
        return feature;
    }
}
