package com.spring.client;

import com.salesforce.emp.connector.BayeuxParameters;
import com.salesforce.emp.connector.EmpConnector;
import com.salesforce.emp.connector.TopicSubscription;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.salesforce.emp.connector.LoginHelper.login;

public class StreamingClient {

    public static final String username = "chintanjshah@gmail.com";
    public static final String password = "Welcome1";
    public static final String topic = "/data/AccountChangeEvent"; // "/event/Error_Exception_Log__e";

    public static void main(String args[]) throws Exception {
        long replayFrom = EmpConnector.REPLAY_FROM_EARLIEST;
        BayeuxParameters params = login(username, password);
        EmpConnector connector = new EmpConnector(params);
        Consumer<Map<String, Object>> consumer = event -> System.out.println(String.format("Received:\n%s", event));
        connector.start().get(5, TimeUnit.SECONDS);
        TopicSubscription subscription = connector.subscribe(topic, replayFrom, consumer ).get(5, TimeUnit.SECONDS);
        System.out.println(String.format("Subscribed: %s", subscription));
        //subscription.cancel();
        //connector.stop();
    }
}
