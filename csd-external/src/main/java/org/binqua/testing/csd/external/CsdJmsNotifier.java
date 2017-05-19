package org.binqua.testing.csd.external;

import javax.jms.Message;

public interface CsdJmsNotifier {

    void notify(Message textMessage);

}
