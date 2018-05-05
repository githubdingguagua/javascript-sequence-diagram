package org.binqua.testing.csd.formatter.report.conversation;

import org.binqua.testing.csd.formatter.svg.MessageDescriptionDictionaryFactoryCreator;
import org.binqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.formatter.svg.SequenceDiagramGeneratorFactory;

public class DecoratedConversationFactory {

    public DecoratedConversation decorate(Conversation conversation) {
        return new DecoratedConversation(
                SequenceDiagramGeneratorFactory.instance(),
                conversation,
                MessageDescriptionDictionaryFactoryCreator.instance());
    }

}
