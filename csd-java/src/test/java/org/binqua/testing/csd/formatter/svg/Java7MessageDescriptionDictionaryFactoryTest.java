package org.binqua.testing.csd.formatter.svg;

import org.junit.Test;
import org.mockito.Mockito;

import org.bniqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.httpclient.HttpMessage;
import org.binqua.testing.csd.httpclient.SimpleIdentifier;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Java7MessageDescriptionDictionaryFactoryTest {

    private final Conversation conversation = Mockito.mock(Conversation.class);

    private final MessageDescriptionDictionaryFactory java7MultipleOccurrencesTextFormatter = new Java7MessageDescriptionDictionaryFactory();

    @Test
    public void givenMessagesWithDuplicatedDescriptionThenCalculateNewDescriptionWorks() throws Exception {

        final Message firstMessage = mock(Message.class);
        final Identifier firstIdentifier = anIdentifierWith("1-2");
        when(firstMessage.identifier()).thenReturn(firstIdentifier);
        when(firstMessage.from()).thenReturn(anAlias("a"));
        when(firstMessage.to()).thenReturn(anAlias("b"));
        when(firstMessage.messageType()).thenReturn(() -> "req");
        when(firstMessage.description()).thenReturn("m2");

        final Message secondMessage = mock(Message.class);
        final Identifier secondIdentifier = anIdentifierWith("2-2");
        when(secondMessage.identifier()).thenReturn(secondIdentifier);
        when(secondMessage.from()).thenReturn(anAlias("a"));
        when(secondMessage.to()).thenReturn(anAlias("b"));
        when(secondMessage.messageType()).thenReturn(() -> "res");
        when(secondMessage.description()).thenReturn("m2");

        when(conversation.messages()).thenReturn(asList(firstMessage, secondMessage));

        MessageDescriptionDictionary messageDescriptionDictionary = java7MultipleOccurrencesTextFormatter.createDictionary(conversation);

        assertThat(messageDescriptionDictionary.get(firstIdentifier), is("1 of 2 m2"));
        assertThat(messageDescriptionDictionary.get(secondIdentifier), is("2 of 2 m2"));

    }

    @Test
    public void givenMessagesWithNoDuplicatedDescriptionThenCalculateNewDescriptionWorks() throws Exception {

        final HttpMessage firstMessage = mock(HttpMessage.class);
        final Identifier theIdentifier = anIdentifierWith("1-2");
        when(firstMessage.identifier()).thenReturn(theIdentifier);
        when(firstMessage.from()).thenReturn(anAlias("a"));
        when(firstMessage.to()).thenReturn(anAlias("b"));
        when(firstMessage.messageType()).thenReturn(() -> "req");
        final String theDescription = "m2";
        when(firstMessage.description()).thenReturn(theDescription);

        when(conversation.messages()).thenReturn(asList(firstMessage));

        MessageDescriptionDictionary messageDescriptionDictionary = java7MultipleOccurrencesTextFormatter.createDictionary(conversation);

        assertThat(messageDescriptionDictionary.get(theIdentifier), is(theDescription));

    }

    private SystemAlias anAlias(final String name) {
        return () -> name;
    }

    private Identifier anIdentifierWith(final String id) {
        return new SimpleIdentifier(id);
    }

}