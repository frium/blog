package top.frium.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @date 2025-03-13 22:25:49
 * @description
 */
public interface UploadFileService {
    void uploadFile(MultipartFile file);
}
