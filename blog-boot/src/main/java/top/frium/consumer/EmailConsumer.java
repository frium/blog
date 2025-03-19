package top.frium.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import top.frium.context.RabbitMQConstant;
import top.frium.uitls.EmailUtil;

import java.io.IOException;

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

    @RabbitListener(queues = RabbitMQConstant.EMAIL_QUEUE, ackMode = "MANUAL")
    public void sendEmailMsg(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            log.info("向 {} 发送邮箱验证码", msg);
            emailUtil.sendVerificationEmail(msg);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("发送邮件失败，消息将被丢弃: {}", msg, e);
            try {
                channel.basicReject(deliveryTag, false);
            } catch (IOException ex) {
                log.error("拒绝消息失败: {}", msg, ex);
            }
        }
    }

}
