package org.binqua.testing.csd.client.mockito;

import org.mockito.listeners.MethodInvocationReport;

import java.util.function.Predicate;

public class ProductionCodeRunningPredicate implements Predicate<MethodInvocationReport> {
    @Override
    public boolean test(MethodInvocationReport methodInvocationReport) {
        return !methodInvocationReport.getInvocation().getLocation().toString().contains("ComponentTest.java");
    }
}
