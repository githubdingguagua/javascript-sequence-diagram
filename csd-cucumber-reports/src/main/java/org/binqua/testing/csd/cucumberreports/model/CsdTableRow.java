package org.binqua.testing.csd.cucumberreports.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class CsdTableRow {

    private static final CsdTableRow EMPTY_DATA_ROW = new CsdTableRow(null);

    private List<String> columnValues;

    private CsdTableRow(List<String> columnValues) {
        this.columnValues = columnValues;
    }

    public static CsdTableRow empty() {
        return EMPTY_DATA_ROW;
    }

    public static CsdTableRow withColumns(List<String> columnValues) {
        return new CsdTableRow(columnValues);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public List<String> getColumnValues() {
        return columnValues;
    }
}
