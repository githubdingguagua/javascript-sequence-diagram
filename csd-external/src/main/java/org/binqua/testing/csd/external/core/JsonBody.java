package org.binqua.testing.csd.external.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JsonBody implements Body {

    @SerializedName("value")
    private final String formattedBodyContent;

    @SerializedName("content-type")
    private final String contentType = ContentType.JSON.toString().toLowerCase();

    transient private final String rawBodyContent;

    public JsonBody(String rawBodyContent) {
        this.rawBodyContent = rawBodyContent;
        this.formattedBodyContent = prettyPrint(rawBodyContent);
    }

    private String prettyPrint(String rowJson) {
        if (isEmpty(rowJson)) {
            return "";
        }
        return new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(new JsonParser().parse(rowJson));
    }

    private boolean isEmpty(String rowJson) {
        return rowJson == null || rowJson.trim().equals("");
    }

    @Override
    public ContentType contentType() {
        return ContentType.JSON;
    }

    @Override
    public JsonElement asJson() {
        return new Gson().toJsonTree(this);
    }

    @Override
    public String rawValue() {
        return rawBodyContent;
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
