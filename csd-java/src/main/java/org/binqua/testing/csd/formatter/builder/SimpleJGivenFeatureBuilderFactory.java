package org.binqua.testing.csd.formatter.builder;

import org.binqua.testing.csd.formatter.util.IdGenerator;

public class SimpleJGivenFeatureBuilderFactory implements JGivenFeatureBuilderFactory {

    private IdGenerator scenarioIdGenerator;
    private IdGenerator featureIdGenerator;

    public SimpleJGivenFeatureBuilderFactory(IdGenerator scenarioIdGenerator, IdGenerator featureIdGenerator) {
        this.scenarioIdGenerator = scenarioIdGenerator;
        this.featureIdGenerator = featureIdGenerator;
    }

    @Override
    public JGivenFeatureBuilder createAFeatureBuilder() {
        return new VerySimpleJGivenFeatureBuilder(
                scenarioIdGenerator,
                featureIdGenerator
        );
    }
}
