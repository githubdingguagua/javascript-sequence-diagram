package org.binqua.testing.csd.client.mockito;

import org.junit.Test;
import org.mockito.invocation.DescribedInvocation;
import org.mockito.invocation.Location;
import org.mockito.listeners.MethodInvocationReport;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProductionCodeRunningPredicateTest {

    private final MethodInvocationReport methodInvocationReport = mock(MethodInvocationReport.class);
    private final DescribedInvocation describedInvocation = mock(DescribedInvocation.class);
    private final Location location = mock(Location.class);

    private final ProductionCodeRunningPredicate productionCodeRunningPredicate = new ProductionCodeRunningPredicate();

    @Test
    public void givenALocationWithComponentTestDotJavaThenIsNotProductionCodeRunning() {
        when(methodInvocationReport.getInvocation()).thenReturn(describedInvocation);
        when(describedInvocation.getLocation()).thenReturn(location);
        when(location.toString()).thenReturn("uk.gov.dwp.universe.component.appointments.BookAppointmentComponentTest$GivenStage.setup_claimant(BookAppointmentComponentTest.java:198)");

        assertThat(productionCodeRunningPredicate.test(methodInvocationReport), is(false));
    }

    @Test
    public void givenALocationWithoutComponentTestDotJavaWordThenIsNotProductionCodeRunning() {
        when(methodInvocationReport.getInvocation()).thenReturn(describedInvocation);
        when(describedInvocation.getLocation()).thenReturn(location);
        when(location.toString()).thenReturn("uk.gov.dwp.component.bla.appointments.BookAppointmentComponentTest$GivenStage.setup_claimant(BookAppointmentSomethingElse.java:198)");

        assertThat(productionCodeRunningPredicate.test(methodInvocationReport), is(true));
    }

    @Test
    public void givenALocationWithDotComponentDotAndDotCoreDotStagesDotGivenStagesDotThenIsNotProductionCodeRunning() {
        when(methodInvocationReport.getInvocation()).thenReturn(describedInvocation);
        when(describedInvocation.getLocation()).thenReturn(location);
        when(location.toString()).thenReturn("-> at uk.gov.dwp.universe.component.core.stages.givenstages.ClaimantExistsGivenStage.lambda$createPersons$6(ClaimantExistsGivenStage.java:466)");

        assertThat(productionCodeRunningPredicate.test(methodInvocationReport), is(false));
    }

}