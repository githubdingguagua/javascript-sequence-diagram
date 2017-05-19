package org.binqua.testing.csd.cucumberreports;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import org.binqua.testing.csd.cucumberreports.model.Feature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MustacheReportPrinter {

    private static final String FEATURE_REPORT_TEMPLATE_FILE = "mustache/featureReport.mustache";

    private final Mustache featureReportTemplate;

    public MustacheReportPrinter() {
        featureReportTemplate = new DefaultMustacheFactory().compile(FEATURE_REPORT_TEMPLATE_FILE);
    }

    public void print(Feature feature, File featureReportDirectory) {
        featureReportDirectory.mkdirs();

        try {
            try (FileWriter fileWriter = new FileWriter(new File(featureReportDirectory, "index.html"))) {
                featureReportTemplate.execute(fileWriter, feature);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create the report", e);
        }
    }
}
