package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.cucumberreports.model.Feature;

public class JsonFeatureReportFactoryGsonImpl implements JsonFeatureReportFactory {
    @Override
    public ToJson createJsonFeatureReport(Feature feature) {
        return new GsonFeatureReport(feature);
    }
}
