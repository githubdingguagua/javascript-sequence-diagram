package org.binqua.testing.csd.external.core;

import com.google.gson.JsonElement;

import java.util.HashMap;

public interface Headers {

    JsonElement asJson();

    Headers withKeyValue(String key, String value);

    HashMap<String, String> httpHeaders();

    String valueOf(String key);

}
