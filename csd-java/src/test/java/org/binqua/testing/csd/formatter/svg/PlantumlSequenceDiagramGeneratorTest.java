package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.httpclient.HttpMessage;
import org.junit.Test;

import org.binqua.testing.csd.external.SystemAlias;
import org.binqua.testing.csd.external.core.Identifier;
import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.external.core.MessageTypeEnumImpl;
import org.binqua.testing.csd.httpclient.HttpResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlantumlSequenceDiagramGeneratorTest {

    private final SvgDecorator svgDecorator = mock(SvgDecorator.class);
    private final Message firstMessage = mock(Message.class);
    private final Identifier firstMessageIdentifier = mock(Identifier.class);
    private final FromPlantumSyntaxToSvg fromPlantumSyntaxToSvg = mock(FromPlantumSyntaxToSvg.class);
    private final MessageDescriptionDictionary messageDescriptionDictionary = mock(MessageDescriptionDictionary.class);

    private final PlantumlSequenceDiagramGenerator plantumlSequenceDiagramGenerator = new PlantumlSequenceDiagramGenerator(svgDecorator, fromPlantumSyntaxToSvg);

    @Test
    public void sequenceDiagramIsCorrectInCaseDescriptionIsDefined() {

        final Map<String, String> newDescriptionMap = new HashMap<>();
        final String firstDescription = "descr1";
        newDescriptionMap.put("id1", firstDescription);
        final String secondDescription = "descr2";
        newDescriptionMap.put("id2", secondDescription);

        when(firstMessage.identifier()).thenReturn(firstMessageIdentifier);
        when(firstMessage.from()).thenReturn(system("SystemA"));
        when(firstMessage.to()).thenReturn(system("SystemB"));
        when(firstMessage.description()).thenReturn("login request");
        when(firstMessage.messageType()).thenReturn(MessageTypeEnumImpl.request);

        final HttpMessage secondMessage = mock(HttpResponse.class);
        final Identifier secondMessageIdentifier = mock(Identifier.class);

        when(secondMessage.identifier()).thenReturn(secondMessageIdentifier);
        when(secondMessageIdentifier.id()).thenReturn("id2");
        when(secondMessage.from()).thenReturn(system("SystemB"));
        when(secondMessage.to()).thenReturn(system("SystemA"));
        when(secondMessage.description()).thenReturn("login response");
        when(secondMessage.messageType()).thenReturn(MessageTypeEnumImpl.response);

        final List<Message> messages = asList(firstMessage, secondMessage);

        final String theSvgToBeDecorated = "theSvgToBeDecorated";
        final String expectedPlantumSyntax = "@startuml\n" +
                "autonumber\n" +
                "SystemA -> SystemB : first message description \n" +
                "SystemB --> SystemA : second message description \n" +
                "@enduml\n";
        when(fromPlantumSyntaxToSvg.generateSvg(expectedPlantumSyntax)).thenReturn(theSvgToBeDecorated);

        final String expectedSvg = "<a></a>";
        when(svgDecorator.decorate(theSvgToBeDecorated, messageDescriptionDictionary, messages)).thenReturn(expectedSvg);

        when(messageDescriptionDictionary.get(firstMessageIdentifier)).thenReturn("first message description");
        when(messageDescriptionDictionary.get(secondMessageIdentifier)).thenReturn("second message description");

        final String actualSvg = plantumlSequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, messages);

        verify(fromPlantumSyntaxToSvg).generateSvg(expectedPlantumSyntax);

        verify(svgDecorator).decorate(theSvgToBeDecorated, messageDescriptionDictionary, messages);

        assertThat(actualSvg, is(expectedSvg));

    }

    @Test
    public void givenNoMessagesThenSVGisTheEmptyString() {

        final String actualSvg = plantumlSequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, Collections.emptyList());

        assertThat(actualSvg, is(""));

    }

    @Test
    public void fromAliasWithWithSpacesAreNotAllowAndIllegalArgumentExceptionWillBeThrown() {

        when(firstMessage.from()).thenReturn(system("An AliasWithASpace"));
        when(firstMessage.to()).thenReturn(system("SystemB"));

        assertThatErrorMessageIsCorrect();

    }

    @Test
    public void toAliasWithWithSpacesAreNotAllowAndIllegalArgumentExceptionWillBeThrown() {

        when(firstMessage.from()).thenReturn(system("A"));
        when(firstMessage.to()).thenReturn(system("An AliasWithASpace"));

        assertThatErrorMessageIsCorrect();

    }

    private void assertThatErrorMessageIsCorrect() {
        when(firstMessage.identifier()).thenReturn(firstMessageIdentifier);
        when(firstMessageIdentifier.id()).thenReturn("id1");
        when(firstMessage.description()).thenReturn("login request");

        try {
            plantumlSequenceDiagramGenerator.sequenceDiagram(messageDescriptionDictionary, singletonList(firstMessage));
            fail(IllegalArgumentException.class + " should have been thrown!");
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), is(equalTo(format("Please not white space in alias name! <%s> is wrong", "An AliasWithASpace"))));
        }
    }

    private SystemAlias system(final String systemName) {
        return () -> systemName;
    }

}
