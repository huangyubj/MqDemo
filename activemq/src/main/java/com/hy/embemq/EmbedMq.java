package com.hy.embemq;

import org.apache.activemq.broker.BrokerContext;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/8
 * Time: 13:12
 * Project: mqdemo
 * 嵌入式MQ,启动一个MQ服务
 */
public class EmbedMq {
    public static final String BIND_ADDRESS = "tcp://localhost:61617";

//    public static void main(String[] args) throws Exception {
//        BrokerService brokerService = new BrokerService();
//        brokerService.addConnector(BIND_ADDRESS);
//        brokerService.setBrokerName("Embe_mq");
//        brokerService.setManagementContext(new ManagementContext());
//        brokerService.start();
//    }


    public static void main(String[] args) throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setBrokerName("EmbedMQ");
        brokerService.addConnector(BIND_ADDRESS);
        brokerService.setManagementContext(new ManagementContext());
        brokerService.start();
    }
}
