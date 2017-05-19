package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ScenarioTest {

    @Test
    public void idAsInt() throws Exception {
        assertThat(aScenario("scenario-12").idAsInt(), is(12));
    }

    @Test
    public void hasTagsDetectsTags() throws Exception {
        assertThat(aScenario(asList(new Tag("tagName", 1))).hasTags(), is(true));
        assertThat(aScenario().hasTags(), is(false));
    }

    @Test
    public void given1Failed2PassedAnd1SkippedTestsThenTestCounterIsCorrect() throws Exception {
        final Scenario scenarioUnderTest = aScenario();
        scenarioUnderTest.getSteps().add(aStep(Status.FAILED));
        scenarioUnderTest.getSteps().add(aStep(Status.PASSED));
        scenarioUnderTest.getSteps().add(aStep(Status.PASSED));
        scenarioUnderTest.getSteps().add(aStep(Status.SKIPPED));

        assertThat(scenarioUnderTest.testCounter(), is(new TestCounter(2, 1, 1)));
    }

    @Test
    public void given2PassedTestsThenTestCounterIsCorrect() throws Exception {
        final Scenario scenarioUnderTest = aScenario();
        scenarioUnderTest.getSteps().add(aStep(Status.PASSED));
        scenarioUnderTest.getSteps().add(aStep(Status.PASSED));

        assertThat(scenarioUnderTest.testCounter(), is(new TestCounter(2, 0, 0)));
    }

    @Test
    public void given2FailedTestsThenTestCounterIsCorrect() throws Exception {
        final Scenario scenarioUnderTest = aScenario();
        scenarioUnderTest.getSteps().add(aStep(Status.FAILED));
        scenarioUnderTest.getSteps().add(aStep(Status.FAILED));

        assertThat(scenarioUnderTest.testCounter(), is(new TestCounter(0, 2, 0)));
    }

    @Test
    public void given1SkippedTestsThenTestCounterIsCorrect() throws Exception {
        final Scenario scenarioUnderTest = aScenario();
        scenarioUnderTest.getSteps().add(aStep(Status.SKIPPED));

        assertThat(scenarioUnderTest.testCounter(), is(new TestCounter(0, 0, 1)));
    }

    private Result aResult(Status status) {
        return new Result(1L, status, "");
    }

    private Step aStep(Status status) {
        return new Step("1", null, 1, "", "", aResult(status), Collections.EMPTY_LIST, CsdDataTable.empty());
    }

    private Scenario aScenario() {
        return new Scenario("scenario-12", "", "", "", 1, new ArrayList<>(), "", Collections.EMPTY_LIST);
    }

    private Scenario aScenario(String id) {
        return new Scenario(id, "", "", "", 1, new ArrayList<>(), "", Collections.EMPTY_LIST);
    }

    private Scenario aScenario(List<Tag> tags) {
        return new Scenario("", "", "", "", 1, new ArrayList<>(), "", tags);
    }
}
