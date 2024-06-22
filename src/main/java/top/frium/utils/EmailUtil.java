package top.frium.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

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

    public void sendVerificationEmail(String to) {
        try {
            // 创建MimeMessage对象
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            // 生成一个六位数的随机数（范围从100000到999999）
            Random random = new Random();
            int number = 100000 + random.nextInt(900000);

            // 设置邮件内容，包括 HTML 样式
            String htmlMsg = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>邮箱登录验证码</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "            padding: 20px;\n" +
                    "            color: #333;\n" +
                    "            margin: 0;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "            background: linear-gradient(135deg, #dfffff, #ffffff);\n" +
                    "            padding: 30px;\n" +
                    "            border-radius: 8px;\n" +
                    "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n" +
                    "            color: #6a6a6a;\n"+
                    "        }\n" +
                    "        h3 {\n" +
                    "            color: #007bff;\n" +
                    "        }\n" +
                    "        p {\n" +
                    "            font-size: 16px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h3>注册验证码</h3>\n" +
                    "        <p>您的验证码是：<span>" + number + "</span></p>\n" +
                    "        <p>验证码五分钟内有效哦。</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";

            // 设置邮件相关信息
            helper.setFrom(userName);
            helper.setTo(to);
            helper.setSubject("frium的博客注册验证码");
            helper.setText(htmlMsg, true); // 第二个参数设置为 true，表示内容是 HTML 格式

            // 发送邮件
            mailSender.send(mimeMessage);

            // 将验证码存入 Redis，设置有效期为 5 分钟
            redisTemplate.opsForValue().set(to, String.valueOf(number), 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            // 可以在这里处理邮件发送失败的逻辑，例如记录日志或发送异常通知
        }
    }

}