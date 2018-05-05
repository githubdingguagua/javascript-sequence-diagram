package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.binqua.testing.csd.formatter.external.Configuration;
import org.binqua.testing.csd.formatter.report.ReportFileNames;
import org.binqua.testing.csd.formatter.report.assets.BuildInfoWriter;
import org.binqua.testing.csd.formatter.report.assets.MultipleAssetsWriter;
import org.binqua.testing.csd.formatter.report.assets.SimpleAssetsWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.ApacheUtilFeatureMenuWriter;
import org.binqua.testing.csd.formatter.report.featuremenu.StringBuilderFeatureMenuContentGenerator;
import org.binqua.testing.csd.formatter.report.featuremenu.TestFeaturesFactory;
import org.binqua.testing.csd.formatter.util.IdGeneratorFactory;
import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.bridge.external.ConversationContextNotifierFactory;
import org.binqua.testing.csd.bridge.external.ConversationHttpMessageObserverFactory;
import org.binqua.testing.csd.bridge.external.ConversationSupport;
import org.binqua.testing.csd.bridge.external.StepContextObserver;
import org.binqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.cucumberreports.MustacheReportPrinter;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.formatter.report.screenshot.ApachePageSourceWriter;
import org.binqua.testing.csd.formatter.report.screenshot.NoImageCropper;
import org.binqua.testing.csd.formatter.report.screenshot.ScreenshotWriter;
import org.binqua.testing.csd.formatter.report.screenshot.SimpleScreenshotImageWriter;
import org.binqua.testing.csd.formatter.report.screenshot.SimpleScreenshotWriter;
import org.binqua.testing.csd.formatter.report.screenshot.ThreadBasedImageWriter;
import org.binqua.testing.csd.formatter.report.screenshot.ThreadBasedSourceWriter;
import org.binqua.testing.csd.formatter.report.screenshot.ThreadBasedThumbnailImageWriter;
import org.binqua.testing.csd.formatter.report.screenshot.ThumbnailImageWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CucumberNotifierFactory {

    private static ReportFileNames reportFileNames;

    private static ConversationSupport conversationSupport;

    private static Configuration configuration;

    private static StepContextObserver stepContextObserver;

    private static class CucumberNotifierHolder {

        private static ApacheJsonWriter jsonWriter = new ApacheJsonWriter();

        private static final CucumberNotifier INSTANCE = new FeatureConversationCucumberNotifierImpl(
                new DecoratedConversationFactory(),
                new FeatureConversationWriterImpl(reportFileNames, jsonWriter),
                new FeatureReportsWriterImpl(
                        reportFileNames,
                        new MustacheReportPrinter(),
                        new FeatureReportWriterImpl(reportFileNames, jsonWriter),
                        new JsonFeatureReportFactoryGsonImpl()
                ),
                new ApacheScenarioScreenshotsWriter(jsonWriter, reportFileNames),
                new ApacheUtilFeatureMenuWriter(new StringBuilderFeatureMenuContentGenerator(), reportFileNames),
                new MultipleAssetsWriter(new SimpleAssetsWriter(reportFileNames), new BuildInfoWriter(configuration)),
                createAScreenshotWriter(),
                conversationSupport,
                stepContextObserver,
                IdGeneratorFactory.scenarioIdGeneratorInstance(),
                IdGeneratorFactory.featureIdGeneratorInstance(),
                new ScenarioReportFactory(),
                new FeatureConversationFactory(),
                new TestFeaturesFactory(configuration),
                configuration
        );

        private static ScreenshotWriter createAScreenshotWriter() {
            if ("true".equals(System.getProperty("useAThreadPool"))) {

                final int numberOfThreads = Integer.valueOf(System.getProperty("numberOfThreads"));
                System.out.println("Using Executors.newFixedThreadPool(..) with " + numberOfThreads + " threads");

                final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

                return new SimpleScreenshotWriter(
                        reportFileNames,
                        new ThreadBasedSourceWriter(executorService, new ApachePageSourceWriter()),
                        new ThreadBasedImageWriter(executorService, new SimpleScreenshotImageWriter()),
                        new ThreadBasedThumbnailImageWriter(executorService, new ThumbnailImageWriter()),
                        new NoImageCropper()
                );
            }

            return new SimpleScreenshotWriter(
                    reportFileNames,
                    new ApachePageSourceWriter(),
                    new SimpleScreenshotImageWriter(),
                    new ThumbnailImageWriter(),
                    new NoImageCropper()
            );
        }

    }

    public static CucumberNotifier instance(Configuration configuration) {
        CucumberNotifierFactory.reportFileNames = ReportFileNamesFactory.instance(configuration.reportDestinationDirectory());
        CucumberNotifierFactory.conversationSupport = createTheConversationSupport(configuration.clusterNamePortMap(), configuration.isGenerateSequenceDiagramEnabled());
        CucumberNotifierFactory.stepContextObserver = ConversationContextNotifierFactory.instance(configuration.isGenerateSequenceDiagramEnabled());
        CucumberNotifierFactory.configuration = configuration;
        return CucumberNotifierHolder.INSTANCE;
    }

    public static CucumberNotifier instance() {
        if (reportFileNames == null) {
            throw new IllegalStateException("Ops! Destination dir not initialised yet");
        }
        return CucumberNotifierHolder.INSTANCE;
    }

    private static ConversationSupport createTheConversationSupport(Map<String, Integer> clusterNamePortMap, boolean sequenceDiagramEnabled) {
        return sequenceDiagramEnabled ? ConversationHttpMessageObserverFactory.hazelcastInstance(clusterNamePortMap) : new DummyConversationSupport();
    }

    static class DummyConversationSupport implements ConversationSupport {

        class DummyConversation implements Conversation {

            @Override
            public JsonElement asJson() {
                return new JsonArray();
            }

            @Override
            public List<Message> messages() {
                return new ArrayList<>();
            }

            @Override
            public Map<StepId, List<Message>> messagesByContext() {
                return new HashMap<>();
            }
        }

        @Override
        public Conversation conversation() {
            return new DummyConversation();
        }

        @Override
        public void clearConversation() {

        }

        @Override
        public boolean hasConversation() {
            return false;
        }
    }
}
