package org.binqua.testing.csd.formatter.external;


import org.binqua.testing.csd.formatter.util.IdGeneratorFactory;

public class SimpleConversationContextsFactory implements ConversationContextsFactory {

    @Override
    public StepContexts createConversationContexts() {
        return new SimpleStepContexts(IdGeneratorFactory.stepsIdGenerator());
    }

}
