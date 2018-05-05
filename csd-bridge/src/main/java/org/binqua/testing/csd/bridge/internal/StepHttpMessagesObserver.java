package org.binqua.testing.csd.bridge.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.binqua.testing.csd.bridge.external.ConversationSupport;
import org.binqua.testing.csd.bridge.external.Messages;
import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.bridge.external.StepContext;
import org.binqua.testing.csd.bridge.external.StepContextObserver;
import org.binqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.external.core.MessageObserver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StepHttpMessagesObserver implements MessageObserver, StepContextObserver, ConversationSupport {

    private Messages<Message> messages;

    private LinkedHashMap<StepId, List<Message>> messagesWithContextLinkedHashMap = new LinkedHashMap<>();

    private HttpMessagesFactory<Message> httpMessagesFactory;

    public StepHttpMessagesObserver(HttpMessagesFactory<Message> httpMessagesFactory) {
        this.httpMessagesFactory = httpMessagesFactory;
        this.messages = httpMessagesFactory.newHttpMessages();
    }

    @Override
    public void notify(Message message) {
        messages.add(message);
    }

    @Override
    public void notifyStepContextStart(StepContext stepContext) {

    }

    @Override
    public void notifyStepContextEnd(StepContext context) {
        messagesWithContextLinkedHashMap.put(context.stepId(), new ArrayList<>(messages.list()));
        messages = httpMessagesFactory.newHttpMessages();
    }

    @Override
    public Conversation conversation() {
        return new Conversation() {
            private LinkedHashMap<StepId, List<Message>> messagesWithContextMap = new LinkedHashMap<>(messagesWithContextLinkedHashMap);

            @Override
            public JsonElement asJson() {
                final JsonArray jsonArray = new JsonArray();
                messagesWithContextMap
                    .keySet()
                    .forEach(stepId -> jsonArray.add(toStepContextJsonObject(stepId, messagesWithContextMap.get(stepId))));
                return jsonArray;
            }

            @Override
            public List<Message> messages() {
                return messagesWithContextMap
                        .values()
                        .stream()
                        .reduce(new ArrayList<>(), (a, b) -> {
                            a.addAll(b);
                            return new ArrayList<>(a);
                        });
            }

            @Override
            public Map<StepId, List<Message>> messagesByContext() {
                return messagesWithContextMap;
            }
        };
    }

    private JsonElement toStepContextJsonObject(StepId stepId, List<? extends Message> httpMessages) {
        final JsonObject jsonElement = new JsonObject();
        jsonElement.add("step", new JsonPrimitive(stepId.asString()));
        jsonElement.add("data", toJsonArray(httpMessages));
        return jsonElement;
    }

    private JsonElement toJsonArray(List<? extends Message> messages) {
        final JsonArray jsonArray = new JsonArray();
        messages.forEach(httpMessage -> jsonArray.add(new JsonParser().parse(httpMessage.asJson())));
        return jsonArray;
    }

    @Override
    public void clearConversation() {
        messagesWithContextLinkedHashMap = new LinkedHashMap<>();
    }

    @Override
    public boolean hasConversation() {
        return messagesWithContextLinkedHashMap.size() > 0;
    }
}
