package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class StepTest {

    @Test
    public void aNullDataTableIsConvertedInAnEmptyOne() throws Exception {

        Step actualStep = aStempWithADataTable(null);

        assertThat(actualStep.hasANonEmptyDataTable(), is(false));
        assertThat(actualStep.getDataTableRows(), hasSize(0));
    }

    private Step aStempWithADataTable(CsdDataTable dataTable) {
        return new Step("", mock(Match.class), 1, "", "", mock(Result.class), Collections.EMPTY_LIST, dataTable);
    }
}