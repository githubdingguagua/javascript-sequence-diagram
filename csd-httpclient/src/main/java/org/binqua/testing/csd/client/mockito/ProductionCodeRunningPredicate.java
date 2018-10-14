package org.binqua.testing.csd.client.mockito;

import org.mockito.listeners.MethodInvocationReport;

import java.util.function.Predicate;

public class ProductionCodeRunningPredicate implements Predicate<MethodInvocationReport> {
    @Override
    public boolean test(MethodInvocationReport methodInvocationReport) {
        final String runningCodeLocationDescription = methodInvocationReport.getInvocation().getLocation().toString();
        return !runningCodeLocationDescription.contains("ComponentTest.java") &&
                !runningCodeLocationDescription.contains(".component.core.stages.givenstages.");
    }
}
