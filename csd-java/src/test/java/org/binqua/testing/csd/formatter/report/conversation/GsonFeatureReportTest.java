package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.cucumberreports.model.*;
import org.binqua.testing.csd.cucumberreports.model.Scenario;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.binqua.testing.csd.JsonTestUtil.asJsonAfterReplaceDoubleQuotes;

public class GsonFeatureReportTest {

    @Test
    public void asJsonCreatesTheRightJson() throws Exception {

        final Step firstStep = createStep("101", "given", "step-name-1");
        final Step secondStep = createStep("102", "when", "step-name-2");
        final Step thirdStep = createStep("103", "then", "step-name-3");
        final Step fourthStep = createStep("104", "then", "step-name-4");

        final Scenario firstScenario = createScenario("scenario-0", "scenario-0-title", asList(firstStep, secondStep, thirdStep));

        final Scenario secondScenario = createScenario("scenario-1", "scenario-1-title", asList(fourthStep));

        List<Scenario> scenarios = asList(firstScenario, secondScenario);

        final Feature feature = createFeature("feature-0", "feature-title", scenarios);

        final String expectedJson = "{" +
                "'id':'feature-0'," +
                "'title':'feature-title'," +
                "'scenarios':[" +
                "{'id':'scenario-0','title':'scenario-0-title'," +
                "'steps':[" +
                "{'id':'101','key':'given','text':'step-name-1'}," +
                "{'id':'102','key':'when','text':'step-name-2'}," +
                "{'id':'103','key':'then','text':'step-name-3'}" +
                "]" +
                "}," +
                "{'id':'scenario-1','title':'scenario-1-title'," +
                "'steps':[" +
                "{'id':'104','key':'then','text':'step-name-4'}" +
                "]" +
                "}" +
                "]" +
                "}";

        asJsonAfterReplaceDoubleQuotes(new GsonFeatureReport(feature), is(expectedJson));

    }

    private Feature createFeature(String id, String name, List<Scenario> scenarios) {
        return new Feature(id, Collections.<Tag>emptyList(), "", name, "", 1, scenarios, "");
    }

    private Scenario createScenario(String id, String name, List<Step> steps) {
        return new Scenario(id, "", name, "", 1, steps, "", Collections.EMPTY_LIST);
    }

    private Step createStep(String id, String keyword, String name) {
        return new Step(id, null, 1, keyword, name, null, Collections.EMPTY_LIST, CsdDataTable.empty());
    }
}