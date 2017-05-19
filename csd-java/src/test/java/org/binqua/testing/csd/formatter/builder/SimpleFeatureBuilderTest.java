package org.binqua.testing.csd.formatter.builder;

import com.shazam.shazamcrest.MatcherAssert;
import gherkin.formatter.model.*;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;
import org.binqua.testing.csd.formatter.util.IdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.bniqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.cucumberreports.model.CsdDataTable;
import org.binqua.testing.csd.cucumberreports.model.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleFeatureBuilderTest {

    private static final String FEATURE_ID_1 = "feature1";
    private static final String SCENARIO_ID_1 = "scenario1";
    private static final String SCENARIO_ID_2 = "scenario2";
    private static final String SCENARIO_ID_3 = "scenario3";
    private static final String SCENARIO_ID_4 = "scenario4";

    private static final StepId STEP_ID_1 = StepId.stepId("step-1");
    private static final StepId STEP_ID_2 = StepId.stepId("step-2");
    private static final StepId STEP_ID_3 = StepId.stepId("step-3");
    private static final StepId STEP_ID_4 = StepId.stepId("step-4");
    private static final StepId STEP_ID_5 = StepId.stepId("step-5");
    private static final StepId STEP_ID_6 = StepId.stepId("step-6");

    private static final DataTableRow DATA_TABLE_ROW_0 = new DataTableRow(Collections.EMPTY_LIST, asList("cell1", "cell2"), 1);
    private static final DataTableRow DATA_TABLE_ROW_1 = new DataTableRow(Collections.EMPTY_LIST, asList("1", "2"), 2);

    private static final CsdDataTable EMPTY_CSD_DATA_ROWS = CsdDataTable.empty();

    private static final CsdDataTable CSD_DATA_ROWS = mock(CsdDataTable.class);

    private final HookListBuilderFactory hookListBuilderFactory = mock(HookListBuilderFactory.class);

    private final HookListBuilder hookListBuilder = mock(HookListBuilder.class);
    private final ToDataRowTransformer toDataRowTransformer = mock(ToDataRowTransformer.class);
    private final List<Map<String, Object>> beforeHookList = new ArrayList<>();
    private final List<Map<String, Object>> afterHookList = new ArrayList<>();

    @Before
    public void setup() {
        when(hookListBuilderFactory.createABuilder()).thenReturn(hookListBuilder);

        when(hookListBuilder.hookList()).thenReturn(beforeHookList);
        when(hookListBuilder.hookList()).thenReturn(afterHookList);
    }

    @Test
    public void shouldBeAbleToBuildAFeature() {
        org.binqua.testing.csd.cucumberreports.model.Feature expectedFeature = buildFeature();

        final IdGenerator scenarioIdGenerator = mock(IdGenerator.class);
        final IdGenerator featureIdGenerator = mock(IdGenerator.class);

        when(featureIdGenerator.idOf("first-feature")).thenReturn(FEATURE_ID_1);
        when(scenarioIdGenerator.backgroundId()).thenReturn(SCENARIO_ID_1).thenReturn(SCENARIO_ID_3);
        when(scenarioIdGenerator.idOf("first-feature;do-something")).thenReturn(SCENARIO_ID_2);
        when(scenarioIdGenerator.idOf("first-feature;do-something-else")).thenReturn(SCENARIO_ID_4);

        when(toDataRowTransformer.toDataRow(null)).thenReturn(EMPTY_CSD_DATA_ROWS);

        when(toDataRowTransformer.toDataRow(asList(DATA_TABLE_ROW_0, DATA_TABLE_ROW_1))).thenReturn(CSD_DATA_ROWS);

        final SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(
                scenarioIdGenerator,
                featureIdGenerator,
                toDataRowTransformer
        );

        runEvents(featureBuilder);

        MatcherAssert.assertThat(featureBuilder.build(), sameBeanAs(expectedFeature));
    }

    private void runEvents(FeatureBuilder featureBuilder) {
        featureBuilder.with(new Feature(newArrayList(), newArrayList(new Tag("@DoItBeforeFirstFeature", 1), new Tag("@1", 2)), "Feature", "first feature", "", 3, "first-feature"), "resources/first.feature");
        featureBuilder.withBeforeHook(new Match(newArrayList(), "Util.beforeDoItBeforeFirstFeature()"), new Result("passed", 3089000L, null, null));
        featureBuilder.withBeforeHook(new Match(newArrayList(), "Util.beforeDoItBeforeScenarioDoSomething()"), new Result("passed", 118000L, null, null));
        featureBuilder.with(new Background(newArrayList(), "Background", "a background", "", 5));
        featureBuilder.with(new Step(newArrayList(), "Given ", "we are happy", 6, null, null), STEP_ID_1);
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.weAreHappy()"));
        featureBuilder.with(new Result("passed", 83556000L, null, null));
        featureBuilder.with(new Scenario(newArrayList(), newArrayList(new Tag("@DoItBeforeScenarioDoSomething", 8)), "Scenario", "do something", "", 9, "first-feature;do-something"));
        featureBuilder.with(new Step(newArrayList(), "Given ", "My microservice is running", 10, null, null), STEP_ID_2);
        featureBuilder.with(new Step(newArrayList(), "When ", "I call the status page", 11, null, null), STEP_ID_3);
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.my_microservice_is_running()"));
        featureBuilder.with(new Result("passed", 197313000L, null, null));
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.i_call_the_status_page()"));
        featureBuilder.with(new Result("passed", 61024000L, null, null));
        featureBuilder.withAfterHook(new Match(newArrayList(), "Util.afterDoItBeforeFirstFeature()"), new Result("passed", 93000L, null, null));
        featureBuilder.withAfterHook(new Match(newArrayList(), "Util.afterDoItBeforeScenarioDoSomething()"), new Result("passed", 81000L, null, null));

        featureBuilder.withBeforeHook(new Match(newArrayList(), "Util.beforeDoItBeforeFirstFeature()"), new Result("passed", 68000L, null, null));
        featureBuilder.withBeforeHook(new Match(newArrayList(), "Util.beforeDoItBeforeScenarioDoSomethingElse()"), new Result("passed", 118000L, null, null));
        featureBuilder.with(new Background(newArrayList(), "Background", "a background", "", 5));
        featureBuilder.with(new Step(newArrayList(), "Given ", "we are happy", 6, null, null), STEP_ID_4);
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.weAreHappy()"));
        featureBuilder.with(new Result("passed", 45000L, null, null));
        featureBuilder.with(new Scenario(newArrayList(), newArrayList(new Tag("@DoItBeforeScenarioDoSomethingElse", 13)), "Scenario", "do something else", "", 14, "first-feature;do-something-else"));
        featureBuilder.with(new Step(newArrayList(), "Given ", "My microservice is running", 15, null, null), STEP_ID_5);
        featureBuilder.with(new Step(newArrayList(), "When ", "I call the status page", 16, aDataTableRowList(), null), STEP_ID_6);
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.my_microservice_is_running()"));
        featureBuilder.with(new Result("passed", 21116000L, null, null));
        featureBuilder.with(new Match(newArrayList(), "FirstStepdefs.i_call_the_status_page()"));
        featureBuilder.with(new Result("passed", 42660000L, null, null));
        featureBuilder.withAfterHook(new Match(newArrayList(), "Util.afterDoItBeforeFirstFeature()"), new Result("passed", 102000L, null, null));
        featureBuilder.withAfterHook(new Match(newArrayList(), "Util.afterDoItBeforeScenarioDoSomethingElse()"), new Result("passed", 122000L, null, null));
    }

    private List<DataTableRow> aDataTableRowList() {
        return asList(
                DATA_TABLE_ROW_0,
                DATA_TABLE_ROW_1
        );
    }

    private org.binqua.testing.csd.cucumberreports.model.Feature buildFeature() {
        List<org.binqua.testing.csd.cucumberreports.model.Step> stepsOne = newArrayList(
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_2.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.my_microservice_is_running()", newArrayList()),
                        10,
                        "Given ",
                        "My microservice is running",
                        new org.binqua.testing.csd.cucumberreports.model.Result(197313000L, Status.PASSED, null),
                        newArrayList(), EMPTY_CSD_DATA_ROWS
                ),
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_3.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.i_call_the_status_page()", newArrayList()),
                        11,
                        "When ",
                        "I call the status page", new org.binqua.testing.csd.cucumberreports.model.Result(61024000L, Status.PASSED, null),
                        newArrayList(),
                        EMPTY_CSD_DATA_ROWS
                )
        );

        List<org.binqua.testing.csd.cucumberreports.model.Step> stepsTwo = newArrayList(
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_5.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.my_microservice_is_running()", newArrayList()),
                        15,
                        "Given ",
                        "My microservice is running",
                        new org.binqua.testing.csd.cucumberreports.model.Result(21116000L, Status.PASSED, null),
                        newArrayList(),
                        EMPTY_CSD_DATA_ROWS
                ),
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_6.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.i_call_the_status_page()", newArrayList()),
                        16,
                        "When ",
                        "I call the status page",
                        new org.binqua.testing.csd.cucumberreports.model.Result(42660000L, Status.PASSED, null),
                        newArrayList(),
                        EMPTY_CSD_DATA_ROWS)
        );

        List<org.binqua.testing.csd.cucumberreports.model.Step> backgroundStepsOne = newArrayList(
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_1.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.weAreHappy()", newArrayList()), 6, "Given ",
                        "we are happy", new org.binqua.testing.csd.cucumberreports.model.Result(83556000L, Status.PASSED, null), newArrayList(), EMPTY_CSD_DATA_ROWS)
        );

        List<org.binqua.testing.csd.cucumberreports.model.Step> backgroundStepsTwo = newArrayList(
                new org.binqua.testing.csd.cucumberreports.model.Step(
                        STEP_ID_4.asString(),
                        new org.binqua.testing.csd.cucumberreports.model.Match("FirstStepdefs.weAreHappy()", newArrayList()), 6, "Given ",
                        "we are happy", new org.binqua.testing.csd.cucumberreports.model.Result(45000L, Status.PASSED, null), newArrayList(), EMPTY_CSD_DATA_ROWS)
        );

        List<org.binqua.testing.csd.cucumberreports.model.Scenario> scenarios = newArrayList(
                new org.binqua.testing.csd.cucumberreports.model.Scenario(
                        SCENARIO_ID_1,
                        "",
                        "a background",
                        "Background",
                        5,
                        backgroundStepsOne,
                        "background",
                        newArrayList()
                ),

                new org.binqua.testing.csd.cucumberreports.model.Scenario(
                        SCENARIO_ID_2,
                        "",
                        "do something",
                        "Scenario",
                        9,
                        stepsOne,
                        "scenario",
                        newArrayList(
                                new org.binqua.testing.csd.cucumberreports.model.Tag("@DoItBeforeScenarioDoSomething", 8)
                        )
                ),

                new org.binqua.testing.csd.cucumberreports.model.Scenario(
                        SCENARIO_ID_3,
                        "",
                        "a background",
                        "Background",
                        5,
                        backgroundStepsTwo,
                        "background",
                        newArrayList()
                ),

                new org.binqua.testing.csd.cucumberreports.model.Scenario(
                        SCENARIO_ID_4,
                        "",
                        "do something else",
                        "Scenario",
                        14,
                        stepsTwo,
                        "scenario",
                        newArrayList(
                                new org.binqua.testing.csd.cucumberreports.model.Tag("@DoItBeforeScenarioDoSomethingElse", 13)
                        )
                )
        );

        return new org.binqua.testing.csd.cucumberreports.model.Feature(
                FEATURE_ID_1,
                newArrayList(new org.binqua.testing.csd.cucumberreports.model.Tag("@DoItBeforeFirstFeature", 1), new org.binqua.testing.csd.cucumberreports.model.Tag("@1", 2)),
                "",
                "first feature",
                "Feature",
                3,
                scenarios,
                "resources/first.feature"
        );
    }
}