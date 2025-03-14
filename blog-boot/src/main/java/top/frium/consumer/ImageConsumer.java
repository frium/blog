package top.frium.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.context.RabbitMQConstant;
import top.frium.uitls.FtpUtils;

import java.util.Base64;
import java.util.Map;

/**
 * @date 2024-08-13 16:13:30
 * @description
 */
@Slf4j
@Component
public class ImageConsumer {
    @Autowired
    FtpUtils ftpUtils;

    @RabbitListener(queues = RabbitMQConstant.IMAGE_QUEUE)
    public void uploadImage(Map<String, Object> message) {
        try {
            String fileName = (String) message.get("fileName");
            log.info("存储文件: " + fileName);
            String fileBase64 = (String) message.get("file");
            byte[] file = Base64.getDecoder().decode(fileBase64);

            // 递增命名，避免文件名冲突
            fileName = getUniqueFileName(fileName);
            log.error(fileName);
            // 上传文件
            ftpUtils.sshSftp(file, fileName);
        } catch (Exception e) {

            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    private String getUniqueFileName(String fileName) {
        String baseName = fileName;
        String extension = "";

        // 如果有后缀，分离文件名和后缀
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            baseName = fileName.substring(0, dotIndex);
            extension = fileName.substring(dotIndex);
        }

        int count = 1;
        String newFileName = fileName;

        // 检查文件是否存在，递增命名
        while (ftpUtils.fileExists(newFileName)) {
            newFileName = baseName + count + extension;
            count++;
        }

        return newFileName;
    }
}
