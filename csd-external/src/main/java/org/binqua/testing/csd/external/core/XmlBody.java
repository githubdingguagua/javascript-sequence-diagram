package org.binqua.testing.csd.external.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlBody implements Body {


    @SerializedName("value")
    private final String formattedBodyContent;

    @SerializedName("content-type")
    private final String contentType = ContentType.XML.toString().toLowerCase();

    transient private final String bodyContent;

    public XmlBody(String bodyContent) {
        this.bodyContent = bodyContent;
        this.formattedBodyContent = prettyPrint(bodyContent);
    }

    @Override
    public ContentType contentType() {
        return ContentType.XML;
    }

    @Override
    public JsonElement asJson() {
        return new Gson().toJsonTree(this);
    }

    @Override
    public String rawValue() {
        return bodyContent;
    }

    public String prettyPrint(String rowXml) {

        if (isEmpty(rowXml)) {
            return "";
        }

        final StreamResult result = new StreamResult(new StringWriter());

        final Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(new StringReader(rowXml)), result);
        } catch (TransformerException e) {
            throw new RuntimeException(String.format("Problem formatting in xml content: %s", rowXml), e);
        }

        return result.getWriter().toString().replaceAll("\"", "'");
    }

    private boolean isEmpty(String rowJson) {
        return rowJson == null || rowJson.trim().equals("");
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
