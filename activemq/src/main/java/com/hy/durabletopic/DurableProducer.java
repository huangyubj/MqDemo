package com.hy.durabletopic;

import com.hy.base.Producer;

import javax.jms.JMSException;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/9
 * Time: 9:54
 * Project: MqDemo
 */
public class DurableProducer extends Producer {

    public DurableProducer() throws JMSException {
        super();
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
    public static void main(String[] args) throws InterruptedException, JMSException {
        DurableProducer producer = new DurableProducer();
        try {
            producer.init();
            for (int i = 0; i < 15; i++) {
                if(i%3 == 0){
//                    producer.sendMessage("你好啊，这是一个P2P消息,只有一个人能收到"+i, QUEUE_NAME);
                }else if(i%3 == 1){
//                    producer.sendMessage("--------这是个批量queue发送的,可以发送组合P2P和TOPIC消息"+i, MANEY_QUEUE_NAME);
                }else{
                    producer.sendTopicMessage("这是个持久化topic消息，每个人都能收到"+i, DUABLE_TOPIC_QUEUE_NAME);
                }
            }
            producer.destory();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
