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
public class Consumer {
    public ConnectionFactory connectionFactory;
    public Connection connection;
    public Session session;
    public String DEFAULT_USERNAME = ActiveMQConnectionFactory.DEFAULT_USER;
    public String DEFAULT_PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    public String DEFAULT_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;

    public Consumer() throws JMSException {
        init();
    }

    protected void init() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

    }

    protected void consumMessage(String queueName) throws JMSException {
        Queue queue = session.createQueue(queueName);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener((message -> {
            printMessage(message);
        }));
    }
    protected void topicConsumMessage(String queueName) throws JMSException {
        Topic topic = session.createTopic(queueName);
        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener((message -> {
            printMessage(message);
        }));
    }
    public void printMessage(Message message){
        if(message instanceof TextMessage){
            try {
                System.out.println("接收到消息："+((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws JMSException {
        Consumer producer1 = new Consumer();
        try {
            producer1.consumMessage(Producer.QUEUE_NAME);
            producer1.topicConsumMessage(Producer.TOPIC_QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
