package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FeatureTest {

    @Test
    public void given2FailedThenTestCounterIsCorrect() throws Exception {
        final Feature feature = aFeature();
        feature.getScenarios().add(aScenario(withFailedTest(2)));

        assertThat(feature.testCounter(), is(new TestCounter(0, 2, 0)));
    }

    @Test
    public void given2PassedTestsThenTestCounterIsCorrect() throws Exception {
        final Feature feature = aFeature();
        feature.getScenarios().add(aScenario(withPassedTest(2)));

        assertThat(feature.testCounter(), is(new TestCounter(2, 0, 0)));
    }

    @Test
    public void given1Failed3PassedAnd2SkippedTestsThenTestCounterIsCorrect() throws Exception {
        final Feature feature = aFeature();
        feature.getScenarios().add(aScenario(withPassedTest(3), withFailedTest(1), withSkippedTest(2)));

        assertThat(feature.testCounter(), is(new TestCounter(3, 1, 2)));
    }

    @Test
    public void hasTagsDetectsTags() throws Exception {
        assertThat(aFeature(asList(new Tag("tagName", 1))).hasTags(), is(true));
        assertThat(aFeature().hasTags(), is(false));
    }

    private Feature aFeature(List<Tag> tag1) {
        return new Feature("", tag1, "", "", "", 12, new ArrayList<>(), "");
    }

    private Feature aFeature() {
        return new Feature("", Collections.EMPTY_LIST, "", "", "", 12, new ArrayList<>(), "");
    }

    private Scenario aScenario(ATestResult... aTestResult) {
        ArrayList<Step> steps = new ArrayList<>();
        for (ATestResult testResult : aTestResult) {
            for (int i = 0; i < testResult.numberOfTest(); i++) {
                steps.add(aStep(testResult.status()));
            }
        }
        return new Scenario("", "", "", "", 1, steps, "", Collections.EMPTY_LIST);
    }

    private ATestResult withFailedTest(int numberOfTest) {
        return new ATestResult(Status.FAILED, numberOfTest);
    }

    private ATestResult withSkippedTest(int numberOfTest) {
        return new ATestResult(Status.SKIPPED, numberOfTest);
    }

    private ATestResult withPassedTest(int numberOfTest) {
        return new ATestResult(Status.PASSED, numberOfTest);
    }

    private class ATestResult {

        private Status status;
        private int numberOfTest;

        public ATestResult(Status status, int numberOfTest) {
            this.status = status;
            this.numberOfTest = numberOfTest;
        }

        public int numberOfTest() {
            return numberOfTest;
        }

        public Status status() {
            return status;
        }
    }

    private Result aResult(Status status) {
        return new Result(1L, status, "");
    }

    private Step aStep(Status status) {
        return new Step("1", null, 1, "", "", aResult(status), Collections.EMPTY_LIST, CsdDataTable.empty());
    }

}
