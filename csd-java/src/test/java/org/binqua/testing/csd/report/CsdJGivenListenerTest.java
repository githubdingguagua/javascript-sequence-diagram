package org.binqua.testing.csd.report;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.verify;

public class CsdJGivenListenerTest {

    private final TestObserver testObserver = Mockito.mock(TestObserver.class);

    private final CsdJGivenListener csdJGivenListener = new CsdJGivenListener(testObserver);

    @Test
    public void givenAScenarioStartedThenFeatureAndScenarioExecutionEventsAreNotifiedProperly() throws NoSuchMethodException {

        csdJGivenListener.scenarioStarted(ATestToBeExecuted.class, ATestToBeExecuted.class.getMethod("firstTestMethod"), Collections.EMPTY_LIST);

        verify(testObserver).notifyFeatureExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted");
        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");

    }

    @Test
    public void givenAScenarioStartedAndFinishedSequenceThenFeatureAndScenarioEventsAreNotifiedProperly() throws NoSuchMethodException {

        csdJGivenListener.scenarioStarted(ATestToBeExecuted.class, ATestToBeExecuted.class.getMethod("firstTestMethod"), Collections.EMPTY_LIST);
        csdJGivenListener.scenarioFinished();

        verify(testObserver).notifyFeatureExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted");
        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");
        verify(testObserver).notifyScenarioExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted" , "org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");

    }

    @Test
    public void given2ScenariosStartedAndFinishedSequenceThenFeatureAndScenarioEventsAreNotifiedProperly() throws NoSuchMethodException {

        csdJGivenListener.scenarioStarted(ATestToBeExecuted.class, ATestToBeExecuted.class.getMethod("firstTestMethod"), Collections.EMPTY_LIST);
        csdJGivenListener.scenarioFinished();

        csdJGivenListener.scenarioStarted(ATestToBeExecuted.class, ATestToBeExecuted.class.getMethod("secondTestMethod"), Collections.EMPTY_LIST);
        csdJGivenListener.scenarioFinished();

        verify(testObserver).notifyFeatureExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted");

        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");
        verify(testObserver).notifyScenarioExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted" , "org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");

        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted.secondTestMethod");
        verify(testObserver).notifyScenarioExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted" , "org.binqua.testing.csd.report.ATestToBeExecuted.secondTestMethod");


    }

    @Test
    public void given2DifferentScenarioStartedAndFinishedSequencesThenFeatureAndScenarioEventsAreNotifiedProperly() throws NoSuchMethodException {

        csdJGivenListener.scenarioStarted(ATestToBeExecuted.class, ATestToBeExecuted.class.getMethod("firstTestMethod"), Collections.EMPTY_LIST);
        csdJGivenListener.scenarioFinished();

        csdJGivenListener.scenarioStarted(AnotherTestToBeExecuted.class, AnotherTestToBeExecuted.class.getMethod("anotherTestMethodToBeExecuted"), Collections.EMPTY_LIST);
        csdJGivenListener.scenarioFinished();

        verify(testObserver).notifyFeatureExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted");
        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");
        verify(testObserver).notifyScenarioExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted" , "org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");

        verify(testObserver).notifyFeatureExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted");
        verify(testObserver).notifyFeatureExecutionStarted("org.binqua.testing.csd.report.AnotherTestToBeExecuted");
        verify(testObserver).notifyScenarioExecutionStarted("org.binqua.testing.csd.report.AnotherTestToBeExecuted.anotherTestMethodToBeExecuted");
        verify(testObserver).notifyScenarioExecutionEnded("org.binqua.testing.csd.report.ATestToBeExecuted" , "org.binqua.testing.csd.report.ATestToBeExecuted.firstTestMethod");

    }
}