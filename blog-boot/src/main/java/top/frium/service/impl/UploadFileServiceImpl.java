package top.frium.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.service.UploadFileService;
import top.frium.uitls.FtpUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @date 2025-03-13 22:25:32
 * @description
 */
@Slf4j
@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Value("${ecs.exposePath}")
    String exposePath;
    @Autowired
    FtpUtils ftpUtils;
    @Value("${ecs.directoryPath}")
    String directoryPath;
    @Override
    public void uploadFile(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        System.out.println("生成的文件名: " + fileName);
        Path filePath = Paths.get(directoryPath, fileName);
        int count = 1;
        while (Files.exists(filePath)) {
            String newFileName = addSuffixToFileName(fileName, count);
            filePath = Paths.get(directoryPath, newFileName);
            count++;
        }
        try {
            Files.createDirectories(Paths.get(directoryPath)); // 创建目录
            Files.copy(file.getInputStream(), filePath);
            System.out.println("文件已保存到: " + filePath);
        } catch (IOException e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    private String addSuffixToFileName(String fileName, int count) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "_" + count + extension;
        }
        return fileName + "_" + count;
    }
}
