package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.client.jms.SimpleJmsMessage;
import org.binqua.testing.csd.httpclient.HttpMessage;
import org.binqua.testing.csd.httpclient.HttpRequest;
import org.binqua.testing.csd.httpclient.SimpleIdentifier;
import org.junit.Test;
import org.mockito.Mockito;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.Message;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClickableSvgDecoratorTest {

    private static final String FIRST_SVG_TEXT_NOT_TO_BE_DECORATED = "<text a=\"#1\">m1</text>";

    private final MessageDescriptionDictionary aMessageDescriptionDictionary = Mockito.mock(MessageDescriptionDictionary.class);

    private final SvgDecorator svgDecorator = new ClickableSvgDecorator();

    @Test
    public void givenAGetHttpRequestThenSvgIsDecoratedProperly() {

        final HttpRequest firstMessage = mock(HttpRequest.class);
        final Identifier theMessageIdentifier = anIdentifierWith("123");

        when(firstMessage.identifier()).thenReturn(theMessageIdentifier);
        when(firstMessage.method()).thenReturn(HttpMessage.HttpMethod.GET);

        final String theNewDescription = "m2";

        when(aMessageDescriptionDictionary.get(theMessageIdentifier)).thenReturn(theNewDescription);

        final String textToBeDecorated = "<text a=\"#1\">m1</text>" +
                "<text a=\"#2\">" + theNewDescription + "</text>" +
                "<text a=\"#3\">m3</text>";

        final List<Message> messages = asList(firstMessage);

        final String expectedSvg =
                FIRST_SVG_TEXT_NOT_TO_BE_DECORATED +
                        "<text a=\"#2\" id=\"123\" class=\"clickable get-style \" >" + theNewDescription + "</text>" +
                        "<text a=\"#3\">m3</text>";

        assertThat(svgDecorator.decorate(textToBeDecorated, aMessageDescriptionDictionary, messages), is(expectedSvg));

    }

    @Test
    public void givenAUndeliveredJmsMessageThenSvgIsDecoratedWithRightClass() {

        final SimpleJmsMessage anUndeliveredJmsMessage = mock(SimpleJmsMessage.class);
        final Identifier theMessageIdentifier = anIdentifierWith("123");

        when(anUndeliveredJmsMessage.identifier()).thenReturn(theMessageIdentifier);
        when(anUndeliveredJmsMessage.deliveryExceptionText()).thenReturn(Optional.of("some content"));

        final String theNewDescription = "m2";

        when(aMessageDescriptionDictionary.get(theMessageIdentifier)).thenReturn(theNewDescription);

        final String textToBeDecorated =
                "<text a=\"#1\">m1</text>" +
                        "<text a=\"#2\">" + theNewDescription + "</text>" +
                        "<text a=\"#3\">m3</text>";

        final List<Message> messages = asList(anUndeliveredJmsMessage);

        final String expectedSvg =
                FIRST_SVG_TEXT_NOT_TO_BE_DECORATED +
                        "<text a=\"#2\" id=\"123\" class=\"clickable jmsMessage undeliveredMessage\" >" + theNewDescription + "</text>" +
                        "<text a=\"#3\">m3</text>";

        assertThat(svgDecorator.decorate(textToBeDecorated, aMessageDescriptionDictionary, messages), is(expectedSvg));

    }

    @Test
    public void givenDeliveredJmsMessageThenSvgIsDecoratedWithRightClass() {

        final SimpleJmsMessage anUndeliveredJmsMessage = mock(SimpleJmsMessage.class);

        final Identifier theMessageIdentifier = anIdentifierWith("123");
        when(anUndeliveredJmsMessage.identifier()).thenReturn(theMessageIdentifier);
        when(anUndeliveredJmsMessage.deliveryExceptionText()).thenReturn(Optional.empty());

        final String theNewDescription = "m2";

        when(aMessageDescriptionDictionary.get(theMessageIdentifier)).thenReturn(theNewDescription);

        final String textToBeDecorated =
                FIRST_SVG_TEXT_NOT_TO_BE_DECORATED +
                        "<text a=\"#2\">" + theNewDescription + "</text>" +
                        "<text a=\"#3\">m3</text>";

        final List<Message> messages = asList(anUndeliveredJmsMessage);

        final String expectedSvg =
                "<text a=\"#1\">m1</text>" +
                        "<text a=\"#2\" id=\"123\" class=\"clickable jmsMessage \" >" + theNewDescription + "</text>" +
                        "<text a=\"#3\">m3</text>";

        assertThat(svgDecorator.decorate(textToBeDecorated, aMessageDescriptionDictionary, messages), is(expectedSvg));

    }

    private Identifier anIdentifierWith(final String id) {
        return new SimpleIdentifier(id);
    }

}