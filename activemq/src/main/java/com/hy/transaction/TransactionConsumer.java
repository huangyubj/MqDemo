package com.hy.transaction;

import com.hy.base.Consumer;
import com.hy.base.Producer;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 16:30
 * Project: mqdemo
 */
public class TransactionConsumer extends Consumer {
    public TransactionConsumer() throws JMSException {
        super();
    }

    @Override
    protected void init() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
    }

    @Override
    protected void consumMessage(String queueName) throws JMSException {
        Queue queue = session.createQueue(queueName);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener((message -> {
            printMessage(message);
            try {
                session.commit();
            } catch (JMSException e) {
                e.printStackTrace();
            }
//            throw new RuntimeException("1111111111111111111111111111");
        }));
    }
    public static void main(String[] args) throws JMSException {
        TransactionConsumer producer1 = new TransactionConsumer();
        try {
            producer1.consumMessage(Producer.QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
