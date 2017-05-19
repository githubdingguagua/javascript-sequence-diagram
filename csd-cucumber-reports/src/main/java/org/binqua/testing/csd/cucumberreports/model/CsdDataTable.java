package org.binqua.testing.csd.cucumberreports.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collections;
import java.util.List;

public class CsdDataTable {

    private static final CsdDataTable EMPTY_DATA_ROW = new CsdDataTable(null);

    private List<CsdTableRow> csdTableRows ;

    public CsdDataTable(List<CsdTableRow> csdTableRows) {
        this.csdTableRows = csdTableRows;
    }

    public static CsdDataTable empty() {
        return EMPTY_DATA_ROW;
    }

    public static CsdDataTable with(List<CsdTableRow> csdTableRows) {
        return new CsdDataTable(csdTableRows);
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

    public List<CsdTableRow> getRows() {
        return csdTableRows == null ? Collections.EMPTY_LIST : csdTableRows;
    }
}

