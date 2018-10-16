package org.binqua.testing.csd.formatter.builder;

import com.google.common.collect.Lists;
import org.binqua.testing.csd.cucumberreports.model.Feature;
import org.binqua.testing.csd.formatter.util.IdGenerator;

import java.util.Collections;

public class VerySimpleJGivenFeatureBuilder implements JGivenFeatureBuilder {

    private IdGenerator featureIdGenerator;
    private Feature feature;

    public VerySimpleJGivenFeatureBuilder(IdGenerator featureIdGenerator) {
        this.featureIdGenerator = featureIdGenerator;
    }

    @Override
    public void withFeature(String featureId) {
        this.feature = new org.binqua.testing.csd.cucumberreports.model.Feature(
                featureIdGenerator.idOf(featureId),
                Collections.emptyList(),
                "",
                featureIdGenerator.idOf(featureId),
                "Feature",
                0,
                Lists.newArrayList(),
                ""
        );
    }

    @Override
    public org.binqua.testing.csd.cucumberreports.model.Feature build() {
        return null;
    }
}
