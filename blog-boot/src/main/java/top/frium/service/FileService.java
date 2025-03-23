package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.frium.pojo.entity.File;

import java.util.List;

/**
 * @date 2025-03-13 22:25:49
 * @description
 */
public interface FileService extends IService<File> {
    String uploadFile(MultipartFile file);

    List<File> getAllFiles();

    void deleteFile(List<Long> fileId);
}
