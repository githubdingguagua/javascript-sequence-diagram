package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.external.core.Message;
import org.binqua.testing.csd.external.core.MessageTypeEnumImpl;

import java.util.List;

import static java.lang.String.format;

class PlantumlSequenceDiagramGenerator implements SequenceDiagramGenerator {

    private SvgDecorator svgDecorator;
    private FromPlantumSyntaxToSvg fromPlantumSyntaxToSvg;

    PlantumlSequenceDiagramGenerator(SvgDecorator svgDecorator, FromPlantumSyntaxToSvg fromPlantumSyntaxToSvg) {
        this.svgDecorator = svgDecorator;
        this.fromPlantumSyntaxToSvg = fromPlantumSyntaxToSvg;
    }

    @Override
    public String sequenceDiagram(MessageDescriptionDictionary messageDescriptionDictionary,
                                  List<Message> messagesForAGivenStep) {
        if (messagesForAGivenStep.isEmpty()) {
            return "";
        }

        return svgDecorator.decorate(
                fromPlantumSyntaxToSvg.generateSvg(generatePlantumlSequenceDiagramSyntax(messageDescriptionDictionary, messagesForAGivenStep)),
                messageDescriptionDictionary,
                messagesForAGivenStep
        );
    }

    private String generatePlantumlSequenceDiagramSyntax(MessageDescriptionDictionary messageDescriptionDictionary, List<Message> httpMessagesForAGivenStep) {
        final StringBuilder plantumlSequenceDiagram = new StringBuilder("@startuml\nautonumber\n");

        httpMessagesForAGivenStep.forEach(httpMessage -> plantumlSequenceDiagram.append(toPlanUmlLineSyntax(httpMessage, messageDescriptionDictionary)));

        plantumlSequenceDiagram.append("@enduml\n");
        return plantumlSequenceDiagram.toString();
    }

    private String toPlanUmlLineSyntax(Message message, MessageDescriptionDictionary messageDescriptionDictionary) {
        final String from = message.from().name();
        checkNoWhiteSpace(from);
        final String to = message.to().name();
        checkNoWhiteSpace(to);
        return format("%s %s %s : %s \n", from, responseOrRequestSymbol(message), to, messageDescriptionDictionary.get(message.identifier()));
    }

    private void checkNoWhiteSpace(String name) {
        if (name.contains(" ")) {
            throw new IllegalArgumentException(format("Please not white space in alias name! <%s> is wrong", name));
        }
    }

    private String responseOrRequestSymbol(Message message) {
        return message.messageType() == MessageTypeEnumImpl.response ? "-->" : "->";
    }
}
