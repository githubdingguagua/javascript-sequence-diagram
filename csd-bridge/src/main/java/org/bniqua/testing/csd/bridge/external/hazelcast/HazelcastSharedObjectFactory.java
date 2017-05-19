package org.bniqua.testing.csd.bridge.external.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.ItemListener;

import org.binqua.testing.csd.external.core.Message;

public class HazelcastSharedObjectFactory {

    private Configuration configuration = new Configuration();

    public IList<? super Message> serverSide(String clusterIdentifier, int hazelcastPort) {
        final Config config = configuration.serverConfig(clusterIdentifier, hazelcastPort);
        final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        return hazelcastInstance.getList(Configuration.MESSAGES_LIST_NAME);
    }

    public IList<? super Message> clientSide(ItemListener<Message> itemListener, String clusterIdentifier, int hazelcastPort) {
        final ClientConfig config = configuration.clientConfig(clusterIdentifier, hazelcastPort);
        final HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(config);
        final IList<Message> httpMessages = hazelcastInstance.getList(Configuration.MESSAGES_LIST_NAME);
        httpMessages.addItemListener(itemListener, true);
        return httpMessages;
    }

}
