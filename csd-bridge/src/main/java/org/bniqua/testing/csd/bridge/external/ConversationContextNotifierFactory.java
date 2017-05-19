package org.bniqua.testing.csd.bridge.external;

public class ConversationContextNotifierFactory {

    private ConversationContextNotifierFactory() {
    }

    public static StepContextObserver instance(boolean sequenceDiagramEnabled) {
        if (!sequenceDiagramEnabled) {
            return new StepContextObserver() {
                @Override
                public void notifyStepContextEnd(StepContext context) {

                }

                @Override
                public void notifyStepContextStart(StepContext stepContext) {

                }
            };
        }
        return ConversationHttpMessageObserverFactory.alreadyInitialisedHazelcastInstance();
    }

}
