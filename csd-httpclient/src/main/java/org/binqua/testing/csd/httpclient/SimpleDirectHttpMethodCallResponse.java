package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.*;

public class SimpleDirectHttpMethodCallResponse implements DirectHttpMethodCallResponse {

    private final String asJson;
    private final SimpleSystemAlias from;
    private final Identifier identifier;
    private String description;
    private Body body;
    private Identifier correlationIdentifier;
    private SimpleSystemAlias to;
    private Headers headers = new SimpleHeaders();

    public SimpleDirectHttpMethodCallResponse(String description,
                                              Body body,
                                              Identifier identifier,
                                              SimpleSystemAlias from,
                                              SimpleSystemAlias to,
                                              Identifier correlationIdentifier) {
        this.description = description;
        this.body = body;
        this.identifier = identifier;
        this.correlationIdentifier = correlationIdentifier;
        this.from = from;
        this.to = to;
        this.asJson = new Gson().toJson(createJsonRepresentation());
    }

    private JsonElement createJsonRepresentation() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("from", from.name());
        jsonObject.addProperty("to", to.name());
        jsonObject.addProperty("type", messageType().name());
        jsonObject.add("headers", headers.asJson());
        jsonObject.add("body", body.asJson());
        jsonObject.addProperty("id", identifier.id());
        jsonObject.addProperty("correlationId", correlationIdentifier.id());
        jsonObject.addProperty("description", description());
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
        return description;
    }

    public String asJson() {
        return asJson;
    }

    @Override
    public Identifier identifier() {
        return identifier;
    }

    @Override
    public Identifier correlationIdentifier() {
        return correlationIdentifier;
    }

    @Override
    public Headers headers() {
        return headers;
    }
    @Override
    public MessageType messageType() {
        return MessageTypeEnumImpl.response;
    }

}
