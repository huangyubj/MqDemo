package com.hy.transaction;

import com.hy.base.Producer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 15:48
 * Project: mqdemo
 */
public class TransationProducer extends Producer {

    public TransationProducer() throws JMSException {
        super();
    }

    @Override
    protected void sendMessage(String msg, String queueName){
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageProducer messageProducer = session.createProducer(queue);
            messageProducer.send(session.createTextMessage(msg));
//            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * activeMq控制台地址：http://127.0.0.1:8161/admin
     * 61616为服务默认端口
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException, JMSException {
        TransationProducer producer = new TransationProducer();
        try {
            producer.init();
            for (int i = 0; i < 15; i++) {
                if(i%3 == 0){
                    String msg = "这是一个P2P消息,只有一个人能收到"+i;
                    producer.sendMessage(msg, QUEUE_NAME);
                    System.out.println("send message:"+msg);
                }
//                else if(i%3 == 1){
//                    producer.sendMessage("--------这是个批量queue发送的,可以发送组合P2P和TOPIC消息"+i, MANEY_QUEUE_NAME);
//                }else{
//                    producer.sendTopicMessage("这是个topic消息，每个人都能收到"+i, TOPIC_QUEUE_NAME);
//                }
            }
            producer.destory();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
