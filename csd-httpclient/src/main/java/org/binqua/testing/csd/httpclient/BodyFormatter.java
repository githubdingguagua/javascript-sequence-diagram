package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonElement;

import org.binqua.testing.csd.external.core.Headers;

public interface BodyFormatter {
    JsonElement format(String value, Headers headers);
}
