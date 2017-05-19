package org.binqua.testing.csd.formatter.svg;

import org.bniqua.testing.csd.bridge.external.Conversation;
import org.binqua.testing.csd.external.core.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

class Java7MessageDescriptionDictionaryFactory implements MessageDescriptionDictionaryFactory {

    @Override
    public MessageDescriptionDictionary createDictionary(Conversation conversation) {
        final List<? extends Message> messages = conversation.messages();

        final Map<String, Integer> maxNumberOfTextOccurrencesMap = createMaxNumberOfTextOccurrencesMapFrom(messages);

        MultipleInvokerTextFormatter multipleInvokerTextFormatter = new MultipleInvokerTextFormatter(maxNumberOfTextOccurrencesMap);

        return new MessageDescriptionDictionary(messages.stream().collect(toMap(Message::identifier, httpMessage -> multipleInvokerTextFormatter.format(httpMessage.description()))));

    }

    private Map<String, Integer> createMaxNumberOfTextOccurrencesMapFrom(List<? extends Message> messages) {
        final Map<String, Integer> maxNumberOfTextOccurrencesMap = new HashMap<>();

        messages.forEach(message -> {
            final String description = message.description();
            if (maxNumberOfTextOccurrencesMap.get(description) != null) {
                maxNumberOfTextOccurrencesMap.put(description, maxNumberOfTextOccurrencesMap.get(description) + 1);
            } else {
                maxNumberOfTextOccurrencesMap.put(description, 1);
            }
        });
        return maxNumberOfTextOccurrencesMap;
    }

}
