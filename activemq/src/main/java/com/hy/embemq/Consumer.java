package com.hy.embemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 13:25
 * Project: mqdemo
 */
public class Consumer {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private String DEFAULT_USERNAME = ActiveMQConnection.DEFAULT_USER;
    private String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private String DEFAULT_URL = EmbedMq.BIND_ADDRESS;

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
                System.out.println("嵌入式ActiveMQ："+((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        Consumer producer1 = new Consumer();
        try {
            producer1.init();
//            producer1.consumMessage(Producer.QUEUE_NAME);
            producer1.topicConsumMessage(Producer.TOPIC_QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
