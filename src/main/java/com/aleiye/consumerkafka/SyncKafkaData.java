package com.aleiye.consumerkafka;

import com.aleiye.consumerkafka.task.KafakSimpleConsumer;
import com.aleiye.consumerkafka.util.ConfigUtils;
import com.aleiye.consumerkafka.util.CuratorUtils;
import com.aleiye.consumerkafka.util.Utils;
import com.aleiye.zkpath.constants.ZKPathConstants;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by aric on 2016/10/25.
 */
public class SyncKafkaData {
    public static void main(String[] args) {
        try {
            CountDownLatch downLatch = new CountDownLatch(1);
            String brokerString = new String(CuratorUtils.getCurator().getData().forPath(ZKPathConstants.KAFKA_BROKER_DEF_PATH));
            String[] brokers = brokerString.split(",");
            final List<String> seeds = Lists.newArrayList();
            final List<String> ports = Lists.newArrayList();
            for (String broker : brokers) {
                if (StringUtils.isNotEmpty(broker)) {
                    String[] splits = broker.split(":");
                    seeds.add(splits[0]);
                    ports.add(splits[1]);
                }
            }

            List<Integer> listPartition = Utils.getPartitions(ConfigUtils.KAFKA_TOPIC);
            for (Integer partition : listPartition) {
                final int p = partition;
                new Thread(new Runnable() {
                    public void run() {
                        KafakSimpleConsumer ksc = new KafakSimpleConsumer();
                        try {
                            ksc.run(Long.MAX_VALUE, ConfigUtils.KAFKA_TOPIC, p, seeds, Integer.parseInt(ports.get(0)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            CuratorUtils.close();
            downLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
