package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.report.screenshot.ScenarioScreenshots;

public class ScenarioReportFactory {

    public ScenarioReport createAScenarioReport(Scenario currentScenario, ScenarioScreenshots scenarioScreenshots, DecoratedConversation decoratedConversation) {
        return new ScenarioReport(currentScenario, scenarioScreenshots, decoratedConversation);
    }
}
