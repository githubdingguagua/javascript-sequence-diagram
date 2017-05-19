package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.binqua.testing.csd.external.core.Headers;

import java.util.HashMap;

public class SimpleHeaders implements Headers {

    @SerializedName("http-headers")
    private HashMap<String, String> httpHeaders = new HashMap<>();

    @Override
    public JsonElement asJson() {
        return new Gson().toJsonTree(this);
    }

    @Override
    public Headers withKeyValue(String key, String value) {
        httpHeaders.put(key, value);
        return this;
    }

    @Override
    public HashMap<String, String> httpHeaders() {
        return httpHeaders;
    }

    @Override
    public String valueOf(String key) {
        return httpHeaders.get(key);
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

