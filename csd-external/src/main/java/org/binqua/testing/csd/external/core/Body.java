package org.binqua.testing.csd.external.core;

import com.google.gson.JsonElement;

public interface Body {

    enum ContentType {
        JSON, TEXT, XML
    }

    ContentType contentType();

    JsonElement asJson();

    String rawValue();
}
