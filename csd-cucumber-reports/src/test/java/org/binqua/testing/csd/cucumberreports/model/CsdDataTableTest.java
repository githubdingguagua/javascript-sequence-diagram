package org.binqua.testing.csd.cucumberreports.model;

import org.junit.Test;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

public class CsdDataTableTest {

    @Test
    public void noRowsMeansAnEmptyListOfRows() throws Exception {
        assertThat(new CsdDataTable(null).getRows(), hasSize(0));
    }

}