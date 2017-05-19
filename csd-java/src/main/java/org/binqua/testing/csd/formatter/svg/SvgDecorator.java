package org.binqua.testing.csd.formatter.svg;

import org.binqua.testing.csd.external.core.Message;

import java.util.List;

public interface SvgDecorator {
    
    String decorate(String toBeDecorated, MessageDescriptionDictionary messageDescriptionDictionary,  List<Message> messages);

}
