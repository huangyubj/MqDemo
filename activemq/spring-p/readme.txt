ActiveMQ与Spring结合-生产者端
    1.通过jmsTemplate模版发送消息，实现MessageCreator，创建消息，queue/TOPIC
    2.applicationContext 配置
        一、ActiveMQ 连接工厂
        二、Spring Caching连接工厂，Spring用于管理真正的ConnectionFactory的ConnectionFactory
        三、定义JmsTemplate的Queue类型，queue、topic
        四、定义Queue、TOPIC监听器(如果需要)