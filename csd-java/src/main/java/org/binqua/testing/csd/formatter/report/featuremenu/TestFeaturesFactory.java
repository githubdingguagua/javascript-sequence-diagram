package org.binqua.testing.csd.formatter.report.featuremenu;

import org.joda.time.DateTime;

import org.binqua.testing.csd.formatter.external.Configuration;

public class TestFeaturesFactory {

    private Configuration configuration;

    public TestFeaturesFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public TestFeatures createTestFeatures() {
        return new TestFeatures(DateTime::new, configuration, new SimpleDurationFormatter());
    }

}
