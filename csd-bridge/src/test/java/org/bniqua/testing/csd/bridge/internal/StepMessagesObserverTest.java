package org.bniqua.testing.csd.bridge.internal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.bniqua.testing.csd.bridge.external.ArrayListHttpMessagesFactory;
import org.bniqua.testing.csd.bridge.external.StepContext;
import org.junit.Test;

import org.bniqua.testing.csd.bridge.external.Conversation;
import org.bniqua.testing.csd.bridge.external.StepId;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.httpclient.HttpMessage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepMessagesObserverTest {

    private final HttpMessage request = mock(HttpMessage.class);

    private StepHttpMessagesObserver conversationHttpMessageNotifier = new StepHttpMessagesObserver(new ArrayListHttpMessagesFactory());

    @Test
    public void givenToStepContextThenConversationsAsJsonIsCorrect() {

        when(request.asJson()).thenReturn(someJsonWithKeyValue("request", 1));

        final HttpMessage response = mock(HttpMessage.class);
        when(response.asJson()).thenReturn(someJsonWithKeyValue("response", 2));

        conversationHttpMessageNotifier.notify(request);
        conversationHttpMessageNotifier.notifyStepContextEnd(StepContext.stepContext("context1", StepId.stepId("step-1")));
        conversationHttpMessageNotifier.notify(response);
        conversationHttpMessageNotifier.notifyStepContextEnd(StepContext.stepContext("context2", StepId.stepId("step-2")));

        final Conversation actualConversation = conversationHttpMessageNotifier.conversation();

        assertThat("conversation size", conversationHttpMessageNotifier.conversation().messages(), hasSize(2));
        assertThat("conversation should exist", conversationHttpMessageNotifier.hasConversation(), is(true));

        assertThat(actualConversation.asJson().isJsonArray(), is(true));

        assertThat(asString(actualConversation.asJson()), is((
                "[{'step':'step-1','data':[{'request':1}]}" +
                ",{'step':'step-2','data':[{'response':2}]}]"
        ).replaceAll("'", "\"")));

    }

    @Test
    public void messagesWithContextIsCorrect() {

        conversationHttpMessageNotifier.notify(request);

        final HttpMessage response1 = mock(HttpMessage.class);
        conversationHttpMessageNotifier.notify(response1);

        final StepContext context1 = StepContext.stepContext("context1", StepId.stepId("1"));
        conversationHttpMessageNotifier.notifyStepContextEnd(context1);

        final HttpMessage request2 = mock(HttpMessage.class);
        conversationHttpMessageNotifier.notify(request2);

        final HttpMessage response2 = mock(HttpMessage.class);
        conversationHttpMessageNotifier.notify(response2);

        final StepContext context2 = StepContext.stepContext("context2", StepId.stepId("2"));
        conversationHttpMessageNotifier.notifyStepContextEnd(context2);

        final Conversation actualConversation = conversationHttpMessageNotifier.conversation();

        final Map<StepId, List<Message>> actualMessagesWithContext = actualConversation.messagesByContext();

        assertThat(actualMessagesWithContext, instanceOf(LinkedHashMap.class));
        assertThat(actualMessagesWithContext.get(context1.stepId()), is(asList(request, response1)));
        assertThat(actualMessagesWithContext.get(context2.stepId()), is(asList(request2, response2)));

    }

    @Test
    public void clearRemoveAllConversations() {

        conversationHttpMessageNotifier.notify(request);
        conversationHttpMessageNotifier.notifyStepContextEnd(StepContext.stepContext("context1", StepId.stepId("1")));

        conversationHttpMessageNotifier.notify(mock(HttpMessage.class));
        conversationHttpMessageNotifier.notifyStepContextEnd(StepContext.stepContext("context2", StepId.stepId("2")));

        conversationHttpMessageNotifier.clearConversation();

        assertThat("conversation size", conversationHttpMessageNotifier.conversation().messages(), hasSize(0));
        assertThat("conversation should exist", conversationHttpMessageNotifier.hasConversation(), is(false));
        assertThat(asString(conversationHttpMessageNotifier.conversation().asJson()), is("[]"));

    }

    private String asString(JsonElement jsonElement) {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create().toJson(jsonElement);
    }

    private String someJsonWithKeyValue(String key, int value) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key, value);
        return new Gson().toJson(jsonObject);
    }

}