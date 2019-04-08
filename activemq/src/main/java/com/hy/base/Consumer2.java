package com.hy.base;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 10:16
 * Project: mqdemo
 */
public class Consumer2 {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private String DEFAULT_USERNAME = ActiveMQConnectionFactory.DEFAULT_USER;
    private String DEFAULT_PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private String DEFAULT_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;

    public void init() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

    }


    public void consumMessage(String queueName) throws JMSException {
        Queue queue = session.createQueue(queueName);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener((message -> {
            printMessage(message);
        }));
    }
    public void topicConsumMessage(String queueName) throws JMSException {
        Topic topic = session.createTopic(queueName);
        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener((message -> {
            printMessage(message);
        }));
    }
    private void printMessage(Message message){
        if(message instanceof TextMessage){
            try {
                System.out.println("接收到消息："+((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Consumer2 producer1 = new Consumer2();
        try {
            producer1.init();
            producer1.consumMessage(Producer.QUEUE_NAME);
            producer1.topicConsumMessage(Producer.TOPIC_QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
