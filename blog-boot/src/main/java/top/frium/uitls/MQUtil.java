package top.frium.uitls;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.frium.context.RabbitMQConstant;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @date 2024-08-13 17:06:21
 * @description
 */
@Component
public class MQUtil {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Value("${ecs.exposePath}")
    String exposePath;


    public String sendFileMessage(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        Map<String, Object> fileData = new HashMap<>();
        fileData.put("fileName", fileName);
        fileData.put("file", Base64.getEncoder().encodeToString(file.getBytes()));
        rabbitTemplate.convertAndSend(RabbitMQConstant.IMAGE_QUEUE, fileData);
        return exposePath + fileName;
    }
}
