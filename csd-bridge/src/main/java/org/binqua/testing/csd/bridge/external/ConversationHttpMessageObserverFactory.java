package org.binqua.testing.csd.bridge.external;

import org.binqua.testing.csd.bridge.external.hazelcast.HazelcastSharedObjectFactory;
import org.binqua.testing.csd.bridge.external.hazelcast.MyItemListener;
import org.binqua.testing.csd.bridge.internal.StepHttpMessagesObserver;
import org.binqua.testing.csd.external.core.MessageObserver;

import java.util.Map;

public class ConversationHttpMessageObserverFactory {

    private static MyItemListener MY_ITEM_LISTENER = new MyItemListener();

    private static Map<String, Integer> clusterNames;

    private ConversationHttpMessageObserverFactory() {
    }

    private static class AcceptanceTestSupportHolder {
        private static final StepHttpMessagesObserver INSTANCE = new StepHttpMessagesObserver(new ArrayListHttpMessagesFactory());
    }

    private static class HazelcastAcceptanceTestSupportHolder {
        private static final StepHttpMessagesObserver INSTANCE = new StepHttpMessagesObserver(new HazelcastHttpMessagesFactory(
            MY_ITEM_LISTENER,
            clusterNames,
            new HazelcastSharedObjectFactory()
        ));
    }

    public static MessageObserver messageObserverInstance() {
        return AcceptanceTestSupportHolder.INSTANCE;
    }

    public static ConversationSupport conversationSupportInstance() {
        return AcceptanceTestSupportHolder.INSTANCE;
    }

    public static StepContextObserver conversationContextNotifierInstance() {
        return AcceptanceTestSupportHolder.INSTANCE;
    }

    public static StepHttpMessagesObserver hazelcastInstance(Map<String, Integer> clusterNamePortMap) {
        ConversationHttpMessageObserverFactory.clusterNames = clusterNamePortMap;
        return HazelcastAcceptanceTestSupportHolder.INSTANCE;
    }

    public static StepHttpMessagesObserver alreadyInitialisedHazelcastInstance() {
        if (HazelcastAcceptanceTestSupportHolder.INSTANCE == null) {
            throw new IllegalStateException("Ops!! It seams you did not call the right method");
        }
        return HazelcastAcceptanceTestSupportHolder.INSTANCE;
    }

}
