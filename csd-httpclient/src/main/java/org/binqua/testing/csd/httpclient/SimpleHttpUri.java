package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.binqua.testing.csd.external.SystemAlias;

public class SimpleHttpUri implements HttpUri {

    private String uri;
    private String description;
    private SystemAlias alias;

    public SimpleHttpUri(SystemAlias alias, String uri) {
        this(alias, uri, uri);
    }

    SimpleHttpUri(SystemAlias alias, String uri, String description) {
        this.alias = alias;
        this.uri = uri;
        this.description = description;
    }

    public String value() {
        return uri;
    }

    public SystemAlias alias() {
        return alias;
    }

    public String description() {
        return description;
    }

    @Override
    public JsonElement asJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.add("uri", new JsonPrimitive(value()));
        jsonObject.add("description", new JsonPrimitive(description()));
        jsonObject.add("alias", new JsonPrimitive(alias().name()));
        return jsonObject;
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
