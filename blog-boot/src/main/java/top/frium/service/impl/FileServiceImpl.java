package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.FileMapper;
import top.frium.pojo.entity.File;
import top.frium.service.FileService;
import top.frium.uitls.FtpUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static top.frium.context.CommonConstant.DATA_TIME_PATTERN;

/**
 * @date 2025-03-13 22:25:32
 * @description
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Value("${ecs.exposePath}")
    String exposePath;
    @Autowired
    FtpUtils ftpUtils;
    @Value("${ecs.directoryPath}")
    String directoryPath;
    @Autowired
    FileMapper fileMapper;
    @Value("${ecs.exposePath}")
    String fileUrl;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
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
            String url = fileUrl + filePath.getFileName();
            double fileSizeInMB = Files.size(filePath) / (1024.0 * 1024.0);
            fileSizeInMB = Math.round(fileSizeInMB * 100.0) / 100.0;
            fileMapper.insert(new File(null, url, filePath.getFileName().toString(),
                    LocalDateTime.now().format(DATA_TIME_PATTERN), fileSizeInMB));
            return url;
        } catch (IOException e) {
            throw new MyException(StatusCodeEnum.ERROR);
        }
    }

    @Override
    public List<File> getAllFiles() {
        return list();
    }

    @Override
    public void deleteFile(List<Long> fileIds) {
        fileIds.forEach(fileId -> {
            File file = fileMapper.selectById(fileId);
            if (file == null) throw new MyException("File not found with ID: " + fileId);
            String filePath = directoryPath + "/" + file.getFileName();
            Path path = Paths.get(filePath);
            try {
                if (Files.exists(path)) Files.delete(path);
            } catch (IOException e) {
                throw new MyException("Failed to delete local file: " + filePath);
            }
            fileMapper.deleteById(fileId);
        });
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
