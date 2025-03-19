package top.frium.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.frium.context.RabbitMQConstant;
import top.frium.uitls.EmailUtil;

/**
 *
 * @date 2024-08-02 13:54:11
 * @description
 */
@Slf4j
@Component
public class EmailConsumer {
    @Autowired
    EmailUtil emailUtil;

    @RabbitListener(queues = RabbitMQConstant.EMAIL_QUEUE, ackMode = "AUTO")
    public void sendEmailMsg(String msg) {
        log.info("向 {} 发送邮箱验证码", msg);
        emailUtil.sendVerificationEmail(msg);
    }


}
