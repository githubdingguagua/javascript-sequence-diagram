package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.cucumberreports.model.Feature;

public interface FeatureIndexWriter {

    void write(String featureName, Feature feature);

}
