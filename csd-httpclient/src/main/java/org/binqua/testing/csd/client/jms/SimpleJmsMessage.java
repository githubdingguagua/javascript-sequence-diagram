package org.binqua.testing.csd.client.jms;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.*;

import java.util.Optional;

import static java.lang.String.format;

public class SimpleJmsMessage implements Message {

    private final SystemAlias from;
    private final SystemAlias to;
    private final String asJson;
    private final Headers headers;
    private final Identifier anIdentifier;
    private final Identifier aCorrelationIdentifier;
    private final Body body;
    private final Optional<String> deliveryExceptionText;

    public SimpleJmsMessage(SystemAlias from,
                            SystemAlias to,
                            Headers headers,
                            Body body,
                            Identifier identifier,
                            Identifier correlationIdentifier,
                            Optional<String> deliveryExceptionText
    ) {
        this.from = from;
        this.to = to;
        this.headers = headers;
        this.anIdentifier = identifier;
        this.aCorrelationIdentifier = correlationIdentifier;
        this.body = body;
        this.deliveryExceptionText = deliveryExceptionText;
        this.asJson = new Gson().toJson(asInternalJson());
    }

    private JsonElement asInternalJson() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("from", from.name());
        jsonObject.addProperty("to", to.name());
        jsonObject.addProperty("type", messageType().toString());
        jsonObject.add("headers", headers.asJson());
        jsonObject.add("body", body.asJson());
        jsonObject.addProperty("id", anIdentifier.id());
        jsonObject.addProperty("correlationId", aCorrelationIdentifier.id());
        jsonObject.addProperty("description", description());
        if (deliveryExceptionText.isPresent()) {
            jsonObject.addProperty("deliveryException", deliveryExceptionText.get());
        }
        return jsonObject;
    }

    @Override
    public SystemAlias from() {
        return from;
    }

    @Override
    public SystemAlias to() {
        return to;
    }

    @Override
    public Body body() {
        return body;
    }

    @Override
    public String description() {
        return format("JMS message from %s to %s", from.name(), to.name());
    }

    @Override
    public String asJson() {
        return asJson;
    }

    @Override
    public Identifier identifier() {
        return anIdentifier;
    }

    @Override
    public Identifier correlationIdentifier() {
        return aCorrelationIdentifier;
    }

    @Override
    public Headers headers() {
        return headers;
    }

    @Override
    public MessageType messageType() {
        return MessageTypeEnumImpl.JMS;
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
        return asJson;
    }

    public Optional<String> deliveryExceptionText() {
        return deliveryExceptionText;
    }
}
