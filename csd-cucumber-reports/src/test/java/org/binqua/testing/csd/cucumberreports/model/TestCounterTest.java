package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestCounterTest {

    @Test
    public void testCounterIsBuiltProperly() throws Exception {

        final TestCounter testCounter = new TestCounter(2 , 1 ,4);

        assertThat(testCounter.numberOfPassedTests(), is(2));
        assertThat(testCounter.numberOfFailedTests(), is(1));
        assertThat(testCounter.numberOfSkippedTests(), is(4));

        assertThat(testCounter.numberOfTotalTests(), is(7));
    }

}