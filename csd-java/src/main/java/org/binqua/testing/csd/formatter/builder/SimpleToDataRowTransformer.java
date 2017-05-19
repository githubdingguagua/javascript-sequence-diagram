package org.binqua.testing.csd.formatter.builder;


import org.binqua.testing.csd.cucumberreports.model.CsdDataTable;

import java.util.List;
import java.util.stream.Collectors;

import static org.binqua.testing.csd.cucumberreports.model.CsdTableRow.withColumns;

public class SimpleToDataRowTransformer implements ToDataRowTransformer {

    @Override
    public CsdDataTable toDataRow(List<gherkin.formatter.model.DataTableRow> gerkinDataTable) {
        if (gerkinDataTable == null) {
            return CsdDataTable.empty();
        }
        return CsdDataTable.with(
                gerkinDataTable
                        .stream()
                        .map(gerkinRow -> withColumns(gerkinRow.getCells()))
                        .collect(Collectors.toList())
        );
    }
}
