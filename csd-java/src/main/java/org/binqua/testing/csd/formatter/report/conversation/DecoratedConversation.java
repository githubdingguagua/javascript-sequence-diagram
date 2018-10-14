package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.*;
import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.formatter.svg.MessageDescriptionDictionary;
import org.binqua.testing.csd.formatter.svg.MessageDescriptionDictionaryFactory;
import org.binqua.testing.csd.formatter.svg.SequenceDiagramGenerator;

import java.util.List;
import java.util.Map;

public class DecoratedConversation implements ToJson {

    private final MessageDescriptionDictionary messageDescriptionDictionary;
    private final SequenceDiagramGenerator sequenceDiagramGenerator;
    private final Conversation conversation;

    public DecoratedConversation(SequenceDiagramGenerator sequenceDiagramGenerator,
                                 Conversation conversation,
                                 MessageDescriptionDictionaryFactory messageDescriptionDictionaryFactory
    ) {
        this.conversation = conversation;
        this.sequenceDiagramGenerator = sequenceDiagramGenerator;
        this.messageDescriptionDictionary = messageDescriptionDictionaryFactory.createDictionary(conversation);
    }

    @Override
    public JsonElement asJson() {
        final Map<StepId, List<Message>> messagesByStep = conversation.messagesByContext();
        final JsonArray jsonArray = new JsonArray();

        messagesByStep
                .keySet().forEach(stepId -> {
            jsonArray.add(stepAsJsonObject(
                    stepId,
                    messagesByStep.get(stepId),
                    sequenceDiagramGenerator,
                    messageDescriptionDictionary
                    )
            );
        });

        return jsonArray;
    }

    private JsonElement stepAsJsonObject(StepId stepId,
                                         List<Message> messagesForAGivenStep,
                                         SequenceDiagramGenerator sequenceDiagramGenerator,
                                         MessageDescriptionDictionary messageDescriptionDictionary) {
        final JsonObject jsonElement = new JsonObject();
        jsonElement.add("step", new JsonPrimitive(stepId.asString()));
        jsonElement.add("messages", toJsonArray(messagesForAGivenStep));
        jsonElement.addProperty("sequenceDiagram", sequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, messagesForAGivenStep).replace("\"", "'"));
        return jsonElement;
    }

    private JsonElement toJsonArray(List<Message> messages) {
        final JsonArray jsonArray = new JsonArray();
        messages.forEach(message -> jsonArray.add(new JsonParser().parse(message.asJson())));
        return jsonArray;
    }
}
