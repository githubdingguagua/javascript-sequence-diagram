package org.binqua.testing.csd.formatter.builder;

import org.binqua.testing.csd.formatter.util.IdGeneratorFactory;

public class SimpleFeatureBuilderFactory implements FeatureBuilderFactory {

    @Override
    public FeatureBuilder createAFeatureBuilder() {
        return new SimpleFeatureBuilder(
                IdGeneratorFactory.scenarioIdGeneratorInstance(),
                IdGeneratorFactory.featureIdGeneratorInstance(),
                new SimpleToDataRowTransformer()
        );
    }
}
