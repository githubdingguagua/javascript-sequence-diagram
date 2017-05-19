package org.binqua.testing.csd.external.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TextBody implements Body {

    @SerializedName("value")
    private final String bodyContent;

    @SerializedName("content-type")
    private final String contentType = ContentType.TEXT.toString().toLowerCase();

    public TextBody(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public ContentType contentType() {
        return ContentType.TEXT;
    }

    @Override
    public JsonElement asJson() {
        return new Gson().toJsonTree(this);
    }

    @Override
    public String rawValue() {
        return bodyContent;
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
