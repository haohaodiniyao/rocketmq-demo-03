package com.example.rocketmqdemo03;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class StringProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("10.20.0.13:9876");
        producer.start();
        Message msg = new Message("polyScosOrder","polyScosOrderTag","polyScosOrderKeys","polyScosOrderTopic".getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);
        producer.shutdown();
    }
}
