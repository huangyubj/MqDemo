package com.hy.durabletopic;

import com.hy.base.Consumer;
import com.hy.base.Producer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/9
 * Time: 9:59
 * Project: MqDemo
 */
public class DurableConsumer extends Consumer {
    public DurableConsumer() throws JMSException {
        super();
    }

    @Override
    protected void init() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_URL);
        connection = connectionFactory.createConnection();
        connection.setClientID("durable_client");
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    protected void topicConsumMessage(String queueName) throws JMSException {
        Topic topic = session.createTopic(queueName);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, queueName);
        topicSubscriber.setMessageListener((message -> {
            printMessage(message);
        }));
    }

    /**
     * TOPIC 消息默认不会做持久化，只会推送给当前在线的消费者
     * 持久化TIPIC消费者
     * 需要设置客户端id：connection.setClientID("Mark");
     * 消息的destination变为 Topic
     * 消费者类型变为TopicSubscriber
     * 消费者创建时变为session.createDurableSubscriber(destination,"任意名字，代表订阅名
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws JMSException {
        DurableConsumer producer1 = new DurableConsumer();
        try {
            producer1.topicConsumMessage(Producer.DUABLE_TOPIC_QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
