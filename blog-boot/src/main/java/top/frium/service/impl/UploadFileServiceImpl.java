package top.frium.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.frium.service.UploadFileService;
import top.frium.uitls.FtpUtils;

import java.io.IOException;
import java.util.*;

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

    @Override
    public void uploadFile(MultipartFile file) {
        String fileName =generateUniqueName(Objects.requireNonNull(file.getOriginalFilename())) ;
        log.info("开始上传文件: {}", fileName);
        try {
            byte[] fileData = file.getBytes(); // 获取文件数据
            ftpUtils.sshSftp(fileData, fileName);
            log.info("文件上传成功: {}", fileName);
        } catch (IOException e) {
            log.error("文件上传失败: {}", fileName, e);
            throw new RuntimeException("文件上传失败", e);
        }

        System.out.println(fileName);
    }

    private String generateUniqueName(String originalName) {
        String ext = originalName.contains(".")
                ? originalName.substring(originalName.lastIndexOf("."))
                : "";
        return UUID.randomUUID() + ext;
    }
}
