package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.bridge.external.Conversation;

public interface MessageDescriptionDictionaryFactory {

    MessageDescriptionDictionary createDictionary(Conversation conversation);

}
