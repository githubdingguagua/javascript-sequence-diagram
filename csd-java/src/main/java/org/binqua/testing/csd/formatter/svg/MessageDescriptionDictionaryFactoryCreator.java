package org.binqua.testing.csd.formatter.svg;

public class MessageDescriptionDictionaryFactoryCreator {

    private static final MessageDescriptionDictionaryFactory messageDescriptionDictionaryFactory = new Java7MessageDescriptionDictionaryFactory();

    public static MessageDescriptionDictionaryFactory instance() {
        return messageDescriptionDictionaryFactory;
    }

}
