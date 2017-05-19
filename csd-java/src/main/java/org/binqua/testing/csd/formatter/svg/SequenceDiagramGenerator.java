package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.external.core.Message;

import java.util.List;

public interface SequenceDiagramGenerator {

    String sequenceDiagram(MessageDescriptionDictionary messageDescriptionDictionary, List<Message> messagesForAGivenStep);

}
