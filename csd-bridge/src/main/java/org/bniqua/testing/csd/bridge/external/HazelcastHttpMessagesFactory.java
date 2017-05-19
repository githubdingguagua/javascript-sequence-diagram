package org.bniqua.testing.csd.bridge.external;

import org.bniqua.testing.csd.bridge.internal.HttpMessagesFactory;
import org.bniqua.testing.csd.bridge.external.hazelcast.HazelcastSharedObjectFactory;
import org.bniqua.testing.csd.bridge.external.hazelcast.MyItemListener;

import java.util.Map;

class HazelcastHttpMessagesFactory implements HttpMessagesFactory {

    private MyItemListener itemListener;

    HazelcastHttpMessagesFactory(MyItemListener itemListener,
                                 Map<String, Integer> clusterNamePortMap,
                                 HazelcastSharedObjectFactory hazelcastSharedObjectFactory) {
        this.itemListener = itemListener;
        clusterNamePortMap.keySet().stream().forEach(
            clusterName -> hazelcastSharedObjectFactory.clientSide(itemListener, clusterName, clusterNamePortMap.get(clusterName))
        );
    }

    @Override
    public Messages newHttpMessages() {
        itemListener.clear();
        return itemListener.httpMessages();
    }
}
