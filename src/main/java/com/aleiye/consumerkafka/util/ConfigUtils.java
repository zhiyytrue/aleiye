package com.aleiye.consumerkafka.util;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by aric on 2016/10/25.
 */
public class ConfigUtils {
    private final static Config config = ConfigFactory.load();
    public final static String ZK_URL = config.getString("system.zookeeper.url");
    public final static String KAFKA_TOPIC = config.getString("system.kafka.topic");
}
