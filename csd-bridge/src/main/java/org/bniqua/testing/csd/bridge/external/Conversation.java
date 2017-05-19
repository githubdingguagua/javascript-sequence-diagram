package org.bniqua.testing.csd.bridge.external;

import com.google.gson.JsonElement;

import org.binqua.testing.csd.external.core.Message;

import java.util.List;
import java.util.Map;

public interface Conversation {

    JsonElement asJson();

    List<Message> messages();

    Map<StepId, List<Message>> messagesByContext();

}
