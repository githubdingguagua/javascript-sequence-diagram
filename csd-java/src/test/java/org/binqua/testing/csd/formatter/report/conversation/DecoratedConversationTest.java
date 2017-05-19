package org.binqua.testing.csd.formatter.report.conversation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.binqua.testing.csd.httpclient.HttpMessage;
import org.junit.Test;

import org.bniqua.testing.csd.bridge.external.Conversation;
import org.bniqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.formatter.svg.MessageDescriptionDictionary;
import org.binqua.testing.csd.formatter.svg.MessageDescriptionDictionaryFactory;
import org.binqua.testing.csd.formatter.svg.SequenceDiagramGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DecoratedConversationTest {

    private final HttpMessage request = mock(HttpMessage.class);
    private final Conversation conversation = mock(Conversation.class);
    private final SequenceDiagramGenerator sequenceDiagramGenerator = mock(SequenceDiagramGenerator.class);
    private final MessageDescriptionDictionary messageDescriptionDictionary = mock(MessageDescriptionDictionary.class);
    private final MessageDescriptionDictionaryFactory messageDescriptionDictionaryFactory = mock(MessageDescriptionDictionaryFactory.class);

    @Test
    public void asJsonIsCorrect() throws Exception {

        when(messageDescriptionDictionaryFactory.createDictionary(conversation)).thenReturn(messageDescriptionDictionary);

        when(request.asJson()).thenReturn(simpleJsonWithKeyValue("request", 1));

        final Message response = mock(Message.class);
        when(response.asJson()).thenReturn(simpleJsonWithKeyValue("response", 2));

        Map<StepId, List<Message>> messagesByContext = new LinkedHashMap<>();
        messagesByContext.put(StepId.stepId("step-1"), asList(request));
        messagesByContext.put(StepId.stepId("step-2"), asList(response));

        when(conversation.messagesByContext()).thenReturn(messagesByContext);

        when(sequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, asList(request))).thenReturn("<svg><a value=\"888\"/>/svg>");
        when(sequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, asList(response))).thenReturn("<svg><a value=\"000\"/>/svg>");

        DecoratedConversation decoratedConversation = new DecoratedConversation(sequenceDiagramGenerator, conversation, messageDescriptionDictionaryFactory);

        assertThat(asString(decoratedConversation.asJson()),
                is(replaceSingleQuotes("[" +
                        "{'step':'step-1','messages':[{'request':1}],'sequenceDiagram':'\\u003csvg\\u003e\\u003ca value\\u003d\\u0027888\\u0027/\\u003e/svg\\u003e'}," +
                        "{'step':'step-2','messages':[{'response':2}],'sequenceDiagram':'\\u003csvg\\u003e\\u003ca value\\u003d\\u0027000\\u0027/\\u003e/svg\\u003e'}" +
                        "]"))
        );

    }


    private String simpleJsonWithKeyValue(String key, int value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        return new Gson().toJson(jsonObject);
    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().create().toJson(jsonElement);
    }

    private String replaceSingleQuotes(java.lang.String expectedJson) {
        return expectedJson.replaceAll("'", "\"");
    }
}