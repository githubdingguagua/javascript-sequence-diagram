package org.binqua.testing.csd.formatter.builder;


import gherkin.formatter.model.DataTableRow;
import org.binqua.testing.csd.cucumberreports.model.CsdDataTable;

import java.util.List;

public interface ToDataRowTransformer {

    CsdDataTable toDataRow(List<DataTableRow> dataTableRows);

}
