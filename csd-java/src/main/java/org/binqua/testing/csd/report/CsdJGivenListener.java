package org.binqua.testing.csd.report;

import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.impl.intercept.ScenarioListener;
import com.tngtech.jgiven.report.model.InvocationMode;
import com.tngtech.jgiven.report.model.NamedArgument;
import io.vavr.control.Option;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static java.lang.String.format;

public class CsdJGivenListener implements ScenarioListener {

    private TestObserver testObserver;
    private Option<String> optionalPreviousFeatureId = Option.none();
    private String currentScenarioId;

    public CsdJGivenListener(TestObserver testObserver) {
        this.testObserver = testObserver;
    }

    @Override
    public void scenarioFailed(Throwable throwable) {

    }

    @Override
    public void scenarioStarted(String s) {

    }

    @Override
    public void scenarioStarted(Class<?> aClass, Method method, List<NamedArgument> list) {
        String currentFeatureId = toId(aClass);

        if (optionalPreviousFeatureId.isEmpty()) {
            testObserver.notifyFeatureExecutionStarted(currentFeatureId);
            optionalPreviousFeatureId = Option.of(toId(aClass));
        } else {
            if (!currentFeatureId.equals(optionalPreviousFeatureId.get())) {
                testObserver.notifyFeatureExecutionEnded(optionalPreviousFeatureId.get(), null);

                testObserver.notifyFeatureExecutionStarted(currentFeatureId);
                optionalPreviousFeatureId = Option.of(toId(aClass));
            }
        }

        currentScenarioId = toScenarioId(aClass, method);
        testObserver.notifyScenarioExecutionStarted(currentScenarioId);
    }

    private String toScenarioId(Class<?> aClass, Method method) {
        return format("%s.%s", aClass.getCanonicalName(), method.getName());
    }

    private String toId(Class<?> aClass) {
        return aClass.getCanonicalName();
    }

    @Override
    public void stepMethodInvoked(Method method, List<NamedArgument> list, InvocationMode invocationMode, boolean b) {

    }

    @Override
    public void introWordAdded(String s) {

    }

    @Override
    public void stepCommentAdded(List<NamedArgument> list) {

    }

    @Override
    public void stepMethodFailed(Throwable throwable) {

    }

    @Override
    public void stepMethodFinished(long l, boolean b) {

    }

    @Override
    public void scenarioFinished() {
        optionalPreviousFeatureId.forEach(x -> testObserver.notifyScenarioExecutionEnded(x, currentScenarioId));
    }

    @Override
    public void attachmentAdded(Attachment attachment) {

    }

    @Override
    public void extendedDescriptionUpdated(String s) {

    }

    @Override
    public void sectionAdded(String s) {

    }

    @Override
    public void tagAdded(Class<? extends Annotation> aClass, String... strings) {

    }
}
