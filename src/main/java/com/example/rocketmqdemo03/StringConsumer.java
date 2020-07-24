package com.example.rocketmqdemo03;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
public class StringConsumer {
    @Resource
    private Topic1Properties properties;
    private DefaultMQPushConsumer consumer;
    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(properties.getConsumerGroup());
        consumer.setNamesrvAddr(properties.getNamesrvAddr());
        consumer.subscribe(properties.getTopic(), properties.getTag());
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt messageExt = msgs.get(0);
            try {
                String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                log.info("消息{}",msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        log.info("消费者{}启动成功",consumer.getInstanceName());
    }
    @PreDestroy
    public void destroy(){
        if(consumer != null){
            consumer.shutdown();
            log.info("消费者{}关闭成功",consumer.getInstanceName());
        }
    }
}
