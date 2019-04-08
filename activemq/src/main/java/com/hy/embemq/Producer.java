package com.hy.embemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 9:59
 * Project: mqdemo
 */
public class Producer {
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private String DEFAULT_USERNAME = ActiveMQConnection.DEFAULT_USER;
    private String DEFAULT_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private String DEFAULT_URL = EmbedMq.BIND_ADDRESS;
    public static final String QUEUE_NAME = "testqueue";
    public static final String TOPIC_QUEUE_NAME = "topictestqueue";
    public static final String OTHER_QUEUE_NAME = "otherqueue";
    public static final String MANEY_QUEUE_NAME = "testqueue,otherqueue,topic://topictestqueue";

    public void init() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

    }

    public void sendMessage(String msg, String queueName) throws JMSException {
        Queue queue = session.createQueue(queueName);
        TextMessage textMessage = session.createTextMessage(msg);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(textMessage);
        messageProducer.close();
        session.commit();
    }

    public void sendTopicMessage(String msg, String queueName) throws JMSException {
        Topic queue = session.createTopic(queueName);
        TextMessage textMessage = session.createTextMessage(msg);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(textMessage);
        session.commit();
        messageProducer.close();
    }

    public void destory() throws JMSException {
        session.close();
        connection.close();
    }

    /**
     * activeMq控制台地址：http://127.0.0.1:8161/admin
     * 61616为服务默认端口
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer();
        try {
            producer.init();
            for (int i = 0; i < 15; i++) {
                if(i%3 == 0){
//                    producer.sendMessage("你好啊，这是一个P2P消息,只有一个人能收到"+i, QUEUE_NAME);
                }else if(i%3 == 1){
//                    producer.sendMessage("--------这是个批量queue发送的,可以发送组合P2P和TOPIC消息"+i, MANEY_QUEUE_NAME);
                }else{
                    producer.sendTopicMessage("这是个topic消息，每个人都能收到"+i, TOPIC_QUEUE_NAME);
                }
            }
            producer.destory();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
