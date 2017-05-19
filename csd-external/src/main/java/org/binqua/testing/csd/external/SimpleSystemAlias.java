package org.binqua.testing.csd.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SimpleSystemAlias implements SystemAlias {

    private String name;

    public SimpleSystemAlias(String name) {
        this.name = name;
    }

    public static SystemAlias system(String name) {
        return new SimpleSystemAlias(name);
    }

    @Override
    public String name() {
        return name;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
