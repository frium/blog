package top.frium.uitls;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.frium.common.MyException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 发送邮箱验证码工具类
 * Created by sxy on 2024/3/11.
 */
@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Value("${spring.mail.username}")
    private String userName;// 用户发送者
    @Autowired
    private TemplateEngine templateEngine;

    public void sendVerificationEmail(String to) {
        try {
            // 创建MimeMessage对象
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            Random random = new Random();
            int number = 100000 + random.nextInt(900000);

            // 使用模板引擎生成邮件内容
            Context context = new Context();
            context.setVariable("verificationCode", number);
            String htmlContent = templateEngine.process("verify", context);
            // 设置邮件相关信息
            helper.setFrom(userName);
            helper.setTo(to);
            helper.setSubject("注册验证码");
            helper.setText(htmlContent, true);
            // 发送邮件
            mailSender.send(mimeMessage);
            // 将验证码存入 Redis，设置有效期为 5 分钟
            redisTemplate.opsForValue().set(to, String.valueOf(number), 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new MyException("邮箱发送失败");
        }
    }

}