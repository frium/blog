package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.FileMapper;
import top.frium.pojo.entity.File;
import top.frium.service.FileService;
import top.frium.uitls.FtpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    @Transactional
    public String uploadFile(MultipartFile file) {
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
        Path targetFilePath = resolveUniqueFilePath(originalFileName);
        try {
            Files.createDirectories(Paths.get(directoryPath));
            String contentType = file.getContentType();
            boolean isImage = contentType != null && contentType.startsWith("image");
            if (isImage) {
                processImageFile(file, targetFilePath);
            } else {
                Files.copy(file.getInputStream(), targetFilePath);
            }
            return saveFileRecord(targetFilePath);
        } catch (IOException e) {
            throw new MyException(StatusCodeEnum.FAIL);
        }
    }

    private Path resolveUniqueFilePath(String fileName) {
        Path filePath = Paths.get(directoryPath, fileName);
        int count = 1;
        while (Files.exists(filePath)) {
            String newName = addSuffixToFileName(fileName, count);
            filePath = Paths.get(directoryPath, newName);
            count++;
        }
        return filePath;
    }

    private void processImageFile(MultipartFile file, Path targetPath) throws IOException {
        Path originalTempFile = Files.createTempFile("img_", "_original");
        Path compressedTempFile = Files.createTempFile("img_", "_compressed");
        try {
            Files.copy(file.getInputStream(), originalTempFile, StandardCopyOption.REPLACE_EXISTING);
            double quality = 0.9;
            double scale = 1.0;
            boolean needCompression = true;
            while (needCompression && quality >= 0.3) {
                try (InputStream in = Files.newInputStream(originalTempFile);
                     OutputStream out = Files.newOutputStream(compressedTempFile)) {
                    Thumbnails.of(in)
                            .scale(scale)
                            .outputFormat("jpg")
                            .outputQuality(quality)
                            .toOutputStream(out);
                }
                long compressedSize = Files.size(compressedTempFile);
                if (compressedSize <= 1024 * 1024) {
                    needCompression = false;
                } else {
                    if (quality > 0.5)quality = Math.max(0.3, quality - 0.15);
                    else scale = Math.max(0.5, scale * 0.9);
                }
                Files.move(compressedTempFile, originalTempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.move(originalTempFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(originalTempFile);
            Files.deleteIfExists(compressedTempFile);
        }
    }

    private String saveFileRecord(Path filePath) throws IOException {
        String url = fileUrl + filePath.getFileName();
        double sizeKB = Files.size(filePath) / 1024.0;
        sizeKB = Math.round(sizeKB * 100) / 100.0;
        fileMapper.insert(new File(
                null,
                url,
                filePath.getFileName().toString(),
                LocalDateTime.now().format(DATA_TIME_PATTERN),
                sizeKB 
        ));
        return url;
    }

    @Override
    public List<File> getAllFiles() {
        return list();
    }

    @Override
    @Transactional
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
