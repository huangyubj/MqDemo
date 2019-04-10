import com.hy.queue.QueueSender;
import com.hy.topic.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: HYu
 * Date: 2019/4/9
 * Time: 15:43
 * Project: MqDemo
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:./WEB-INF/config/applicationContext.xml")
public class TestMq {

    @Resource
    QueueSender queueSender;
    @Resource
    TopicSender topicSender;

    @Test
    public void test1(){
        String message = " send a queue message，only one can receive";
        try {
            queueSender.send("test.queue", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
