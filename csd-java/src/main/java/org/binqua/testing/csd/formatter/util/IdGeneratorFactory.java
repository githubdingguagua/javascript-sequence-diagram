package org.binqua.testing.csd.formatter.util;

public class IdGeneratorFactory {

    private static final IdGenerator FEATURE_ID_GENERATOR = new MapBasedIdGenerator("feature");
    private static final IdGenerator SCENARIO_ID_GENERATOR = new ScenarioIdGenerator();
    private static final StepIdGenerator STEPS_ID_GENERATOR = new AtomicIntegerStepIdGenerator();

    public static IdGenerator featureIdGeneratorInstance() {
        return FEATURE_ID_GENERATOR;
    }

    public static IdGenerator scenarioIdGeneratorInstance() {
        return SCENARIO_ID_GENERATOR;
    }

    public static StepIdGenerator stepsIdGenerator() {
        return STEPS_ID_GENERATOR;
    }



}
