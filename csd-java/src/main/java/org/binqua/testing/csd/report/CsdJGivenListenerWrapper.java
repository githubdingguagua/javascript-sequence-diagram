package org.binqua.testing.csd.report;

import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.impl.ScenarioModelBuilder;
import com.tngtech.jgiven.impl.intercept.ScenarioListener;
import com.tngtech.jgiven.report.model.InvocationMode;
import com.tngtech.jgiven.report.model.NamedArgument;
import io.vavr.control.Option;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class CsdJGivenListenerWrapper implements ScenarioListener {

    private TestObserver testObserver;
    private Option<String> optionalPreviousFeatureId = Option.none();
    private String currentScenarioId;
    private ScenarioModelBuilder scenarioModelBuilder;
    private ScenarioListener scenarioListener;

    public CsdJGivenListenerWrapper(ScenarioModelBuilder scenarioModelBuilder, ScenarioListener scenarioListener) {
        this.scenarioModelBuilder = scenarioModelBuilder;
        this.scenarioListener = scenarioListener;
    }

    @Override
    public void scenarioFailed(Throwable throwable) {
        scenarioModelBuilder.scenarioFailed(throwable);
        scenarioListener.scenarioFailed(throwable);
    }

    @Override
    public void scenarioStarted(String s) {
        scenarioModelBuilder.scenarioStarted(s);
        scenarioListener.scenarioStarted(s);
    }

    @Override
    public void scenarioStarted(Class<?> aClass, Method method, List<NamedArgument> list) {
        scenarioModelBuilder.scenarioStarted(aClass, method, list);
        scenarioListener.scenarioStarted(aClass, method, list);
    }


    @Override
    public void stepMethodInvoked(Method method, List<NamedArgument> list, InvocationMode invocationMode, boolean b) {
        scenarioModelBuilder.stepMethodInvoked(method, list, invocationMode, b);
        scenarioListener.stepMethodInvoked(method, list, invocationMode, b);
    }

    @Override
    public void introWordAdded(String s) {
        scenarioModelBuilder.introWordAdded(s);
        scenarioListener.introWordAdded(s);
    }

    @Override
    public void stepCommentAdded(List<NamedArgument> list) {
        scenarioModelBuilder.stepCommentAdded(list);
        scenarioListener.stepCommentAdded(list);
    }

    @Override
    public void stepMethodFailed(Throwable throwable) {
        scenarioModelBuilder.stepMethodFailed(throwable);
        scenarioListener.stepMethodFailed(throwable);
    }

    @Override
    public void stepMethodFinished(long l, boolean b) {
        scenarioModelBuilder.stepMethodFinished(l, b);
        scenarioListener.stepMethodFinished(l, b);
    }

    @Override
    public void scenarioFinished() {
        scenarioModelBuilder.scenarioFinished();
        scenarioListener.scenarioFinished();
    }

    @Override
    public void attachmentAdded(Attachment attachment) {
        scenarioModelBuilder.attachmentAdded(attachment);
        scenarioListener.attachmentAdded(attachment);
    }

    @Override
    public void extendedDescriptionUpdated(String s) {
        scenarioModelBuilder.extendedDescriptionUpdated(s);
        scenarioListener.extendedDescriptionUpdated(s);
    }

    @Override
    public void sectionAdded(String s) {
        scenarioModelBuilder.sectionAdded(s);
        scenarioListener.sectionAdded(s);
    }


    @Override
    public void tagAdded(Class<? extends Annotation> aClass, String... strings) {
        scenarioModelBuilder.tagAdded(aClass, strings);
        scenarioListener.tagAdded(aClass, strings);
    }
}
