package org.binqua.testing.csd.httpclient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.binqua.testing.csd.external.core.Identifier;

public class SimpleIdentifier implements Identifier {

    private String id;

    public SimpleIdentifier(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return id;
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
        return ToStringBuilder.reflectionToString(this);
    }

}
