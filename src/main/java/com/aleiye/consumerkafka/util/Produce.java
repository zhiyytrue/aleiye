package com.aleiye.consumerkafka.util;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by aric on 2016/10/26.
 */
public class Produce {
    public static final String TOPIC = "topic";
    public static final String BATCH_SIZE = "batchSize";
    public static final String MESSAGE_SERIALIZER_KEY = "serializer.class";
    public static final String KEY_SERIALIZER_KEY = "key.serializer.class";
    public static final String BROKER_LIST_KEY = "metadata.broker.list";
    public static final String REQUIRED_ACKS_KEY = "request.required.acks";
    public static final String BROKER_LIST_FLUME_KEY = "brokerList";
    public static final String REQUIRED_ACKS_FLUME_KEY = "requiredAcks";
    public static final String PRODUCER_TYPE_KEY = "producer.type";
    public static final String QUEUE_BUFFERING_MAX_MS_KEY = "queue.buffering.max.ms";
    public static final String QUEUE_BUFFERING_MAX_MESSAGES_KEY = "queue.buffering.max.messages";
    public static final String QUEUE_ENQUEUE_TIMEOUT_MS_KEY = "queue.enqueue.timeout.ms";
    public static final String BATCH_NUM_MESSAGES_KEY = "batch.num.messages";
    public static final String MESSAGE_SEND_MAX_RETRIES_KEY = "message.send.max.retries";
    public static final String RETRY_BACKOFF_MS_KEY = "retry.backoff.ms";
    public static final String REQUEST_TIMEOUT_MS_KEY = "request.timeout.ms";
    // public static final String TOPIC_METADATA_REFRESH_INTERVAL_MS_KEY =
    // "topic.metadata.refresh.interval.ms";

    public static final int DEFAULT_BATCH_SIZE = 200;
    public static final String DEFAULT_TOPIC = "default-flume-topic";
    public static final String DEFAULT_MESSAGE_SERIALIZER = "kafka.serializer.DefaultEncoder";
    public static final String DEFAULT_KEY_SERIALIZER = "kafka.serializer.StringEncoder";
    public static final String PRODUCER_TYPE = "async";
    public static final String DEFAULT_REQUIRED_ACKS = "1";
    public static final String QUEUE_BUFFERING_MAX_MS = "5000";
    public static final String QUEUE_BUFFERING_MAX_MESSAGES = "2000";
    public static final String QUEUE_ENQUEUE_TIMEOUT_MS = "-1";
    public static final String BATCH_NUM_MESSAGES = "200";
    public static final String MESSAGE_SEND_MAX_RETRIES = "10";
    public static final String RETRY_BACKOFF_MS = "1000";
    public static final String REQUEST_TIMEOUT_MS = "30000";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(MESSAGE_SERIALIZER_KEY, DEFAULT_MESSAGE_SERIALIZER);
        props.put(KEY_SERIALIZER_KEY, DEFAULT_KEY_SERIALIZER);
        props.put(REQUIRED_ACKS_KEY, DEFAULT_REQUIRED_ACKS);
        props.put(PRODUCER_TYPE_KEY, PRODUCER_TYPE);
        props.put(QUEUE_BUFFERING_MAX_MS_KEY, QUEUE_BUFFERING_MAX_MS);
        props.put(QUEUE_BUFFERING_MAX_MESSAGES_KEY,
                QUEUE_BUFFERING_MAX_MESSAGES);
        props.put(QUEUE_ENQUEUE_TIMEOUT_MS_KEY, QUEUE_ENQUEUE_TIMEOUT_MS);
        props.put(BATCH_NUM_MESSAGES_KEY, BATCH_NUM_MESSAGES);
        props.put(MESSAGE_SEND_MAX_RETRIES_KEY, MESSAGE_SEND_MAX_RETRIES);
        props.put(RETRY_BACKOFF_MS_KEY, RETRY_BACKOFF_MS);
        props.put(REQUEST_TIMEOUT_MS_KEY, REQUEST_TIMEOUT_MS);
        props.put("metadata.broker.list", "10.0.1.132:9092");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, byte[]> producer = new Producer<String, byte[]>(config);

        List<String> lines = FileUtils.readLines(new File("/Users/arichchurch/Downloads/org－092314.txt"));
        int i = 0;
        for(String line : lines){
            String key = String.valueOf('a');
            if(i%2==0){
                key = String.valueOf('b');
            }
            i++;
            KeyedMessage<String, byte[]> message = new KeyedMessage<String, byte[]>(
                    "C_0", key, line.getBytes());
            producer.send(message);
        }
        System.out.println("read finished! sleep 10s");
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.close();
    }
}
