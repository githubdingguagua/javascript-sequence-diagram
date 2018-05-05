package org.binqua.testing.csd.formatter.external;

import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.formatter.util.StepIdGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class SimpleStepContexts implements StepContexts {

    private final List<StepContext> steps = new ArrayList<>();
    private Iterator<StepContext> stepsIterator;
    private boolean matchingAlreadyStarted;
    private StepIdGenerator stepIdGenerator;
    private StepContext lastStepContextMatched;

    public SimpleStepContexts(StepIdGenerator stepIdGenerator) {
        this.stepIdGenerator = stepIdGenerator;
    }

    @Override
    public StepId recordStep(String name) {
        final StepId stepId = stepIdGenerator.next();
        steps.add(StepContext.stepContext(name, stepId));
        return stepId;
    }

    @Override
    public StepContext recordStepMatch() {
        if (!matchingAlreadyStarted) {
            stepsIterator = steps.iterator();
        }
        matchingAlreadyStarted = true;
        lastStepContextMatched = stepsIterator.next();
        return lastStepContextMatched;
    }

    private String message() {
        return format("Recorded %s steps:\n%s\nbut match never called yet. Something went wrong!",
                steps.size(),
                steps.stream().map(s -> s.stepId().asString()).collect(Collectors.joining("\n")));
    }

    @Override
    public StepContext lastStepContextMatched() {
        if (stepsIterator == null) {
            throw new IllegalStateException(message());
        }
        return lastStepContextMatched;
    }
}
