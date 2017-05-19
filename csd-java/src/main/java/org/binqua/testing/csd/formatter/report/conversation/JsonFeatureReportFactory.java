package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.cucumberreports.model.Feature;

public interface JsonFeatureReportFactory {
    ToJson createJsonFeatureReport(Feature feature);
}
