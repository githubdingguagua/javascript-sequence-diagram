package org.binqua.testing.csd.report;

import com.tngtech.jgiven.impl.ScenarioModelBuilder;
import com.tngtech.jgiven.impl.intercept.ScenarioListener;
import org.binqua.testing.csd.bridge.external.ConversationHttpMessageObserverFactory;
import org.binqua.testing.csd.cucumberreports.MustacheReportPrinter;
import org.binqua.testing.csd.formatter.builder.SimpleJGivenFeatureBuilderFactory;
import org.binqua.testing.csd.formatter.external.Configuration;
import org.binqua.testing.csd.formatter.external.ConfigurationFactory;
import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.assets.BuildInfoWriter;
import org.binqua.testing.csd.formatter.report.assets.MultipleAssetsWriter;
import org.binqua.testing.csd.formatter.report.assets.SimpleAssetsWriter;
import org.binqua.testing.csd.formatter.report.conversation.*;
import org.binqua.testing.csd.formatter.report.featuremenu.ApacheUtilFeatureMenuWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.StringBuilderFeatureMenuContentGenerator;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeaturesFactory;
import org.binqua.testing.csd.formatter.util.IdGeneratorFactory;

public class CsdInstancesFactory {

    private static ApacheJsonWriter jsonWriter = new ApacheJsonWriter();
    private static ScenarioModelBuilder scenarioModelBuilder = null;

    private static final ReportFileNames reportFileNames = new ReportFileNamesImpl(TestObserverHolder.configuration.reportDestinationDirectory(),
            IdGeneratorFactory.featureIdGeneratorInstance(),
            IdGeneratorFactory.scenarioIdGeneratorInstance()
    );

    private static class CsdJGivenListenerHolder {
        private static final ScenarioListener INSTANCE = new CsdJGivenListenerWrapper(CsdInstancesFactory.scenarioModelBuilder, new CsdJGivenListener(TestObserverHolder.INSTANCE));
    }

    private static class TestObserverHolder {

        private static Configuration configuration = new ConfigurationFactory(System.getProperties()).createConfiguration();

        private static final TestObserver INSTANCE = new JGivenTestObserver(
                IdGeneratorFactory.scenarioIdGeneratorInstance(),
                IdGeneratorFactory.featureIdGeneratorInstance(),
                new FeatureConversationFactory(),
                ConversationHttpMessageObserverFactory.conversationSupportInstance(),
                new DecoratedConversationFactory(),
                new ScenarioReportFactory(),
                new FeatureConversationWriterImpl(reportFileNames, jsonWriter),
                new FeatureReportsWriterImpl(
                        reportFileNames,
                        new MustacheReportPrinter(),
                        new FeatureReportWriterImpl(reportFileNames, jsonWriter),
                        new JsonFeatureReportFactoryGsonImpl()
                ),
                new TestFeaturesFactory(configuration),
                new MultipleAssetsWriter(new SimpleAssetsWriter(reportFileNames), new BuildInfoWriter(configuration)),
                new ApacheUtilFeatureMenuWriter(new StringBuilderFeatureMenuContentGenerator(), reportFileNames),
                new SimpleJGivenFeatureBuilderFactory(IdGeneratorFactory.scenarioIdGeneratorInstance(), IdGeneratorFactory.featureIdGeneratorInstance())

        );
    }

    public ScenarioListener instance(ScenarioModelBuilder scenarioModelBuilder) {
        CsdInstancesFactory.scenarioModelBuilder = scenarioModelBuilder;
        return CsdJGivenListenerHolder.INSTANCE;
    }

}
