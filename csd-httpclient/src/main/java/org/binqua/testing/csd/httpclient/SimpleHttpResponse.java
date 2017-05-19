package org.binqua.testing.csd.httpclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.binqua.testing.csd.external.SimpleSystemAlias;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Body;
import org.binqua.testing.csd.external.core.Headers;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.MessageType;
import org.binqua.testing.csd.external.core.MessageTypeEnumImpl;

public class SimpleHttpResponse implements HttpResponse {

    private final String description;
    private final Body body;
    private final Identifier identifier;
    private final int status;
    private final Headers headers;
    private final SimpleSystemAlias from;
    private final SimpleSystemAlias to;
    private final Identifier correlationIdentifier;
    private final String asJson;

    SimpleHttpResponse(String description,
                       Body body,
                       Identifier identifier,
                       int status,
                       Headers headers,
                       SimpleSystemAlias from,
                       SimpleSystemAlias to,
                       Identifier correlationIdentifier) {
        this.correlationIdentifier = correlationIdentifier;
        this.description = description;
        this.body = body;
        this.identifier = identifier;
        this.status = status;
        this.headers = headers;
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
        jsonObject.addProperty("status", status);
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
    public MessageType messageType() {
        return MessageTypeEnumImpl.response;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public Headers headers() {
        return headers;
    }
}
