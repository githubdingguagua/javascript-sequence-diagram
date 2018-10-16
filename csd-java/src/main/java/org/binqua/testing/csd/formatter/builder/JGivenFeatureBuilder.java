package org.binqua.testing.csd.formatter.builder;

public interface JGivenFeatureBuilder {

    void withFeature(String featureId);

    void withScenario(String scenarioId);

    org.binqua.testing.csd.cucumberreports.model.Feature build();

}
