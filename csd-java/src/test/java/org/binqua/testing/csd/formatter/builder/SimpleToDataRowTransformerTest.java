package org.binqua.testing.csd.formatter.builder;

import org.binqua.testing.csd.cucumberreports.model.CsdDataTable;
import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.binqua.testing.csd.cucumberreports.model.CsdTableRow.withColumns;

public class SimpleToDataRowTransformerTest {

    private static final gherkin.formatter.model.DataTableRow DATA_TABLE_ROW_0 = new gherkin.formatter.model.DataTableRow(Collections.EMPTY_LIST, asList("cell1", "cell2"), 1);
    private static final gherkin.formatter.model.DataTableRow DATA_TABLE_ROW_1 = new gherkin.formatter.model.DataTableRow(Collections.EMPTY_LIST, asList("1", "2"), 2);

    private ToDataRowTransformer toDataRowTransformer = new SimpleToDataRowTransformer();

    @Test
    public void transformATableWith2RowsWith2Columns() throws Exception {

        CsdDataTable expectedCsdDataRow = CsdDataTable.with(asList(
                        withColumns(asList(DATA_TABLE_ROW_0.getCells().get(0), DATA_TABLE_ROW_0.getCells().get(1))),
                        withColumns(asList(DATA_TABLE_ROW_1.getCells().get(0), DATA_TABLE_ROW_1.getCells().get(1)))
                )
        );

        assertThat(toDataRowTransformer.toDataRow(asList(DATA_TABLE_ROW_0, DATA_TABLE_ROW_1)), is(expectedCsdDataRow));

    }

    @Test
    public void transformToAnEmptyTableANullValue() throws Exception {

        assertThat(toDataRowTransformer.toDataRow(null), is(CsdDataTable.empty()));

    }
}