package org.binqua.testing.csd.httpclient;

import com.google.gson.JsonElement;

import org.binqua.testing.csd.external.SystemAlias;

public interface HttpUri{

    JsonElement asJson();

    String value();

    String description();

    SystemAlias alias();
}
