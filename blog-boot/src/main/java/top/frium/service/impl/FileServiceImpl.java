package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
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

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Iterator;
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

    private static final long MAX_IMAGE_SIZE_BYTES = 1024 * 1024; // 1MB

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
                try (InputStream in = file.getInputStream()) {
                    Files.copy(in, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            return saveFileRecord(targetFilePath);
        } catch (IOException e) {
            log.error("File upload failed: {}", e.getMessage(), e);
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
        try (InputStream in = file.getInputStream()) {
            byte[] originalBytes = file.getBytes();
            if (originalBytes.length <= MAX_IMAGE_SIZE_BYTES) {
                Files.write(targetPath, originalBytes);
                return;
            }
            String formatName = getImageFormat(originalBytes);
            if (formatName == null) formatName = "jpg";
            BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(originalBytes));
            if (inputImage == null) throw new IOException("Failed to read image file.");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (ImageIO.write(inputImage, formatName, baos) && baos.size() <= MAX_IMAGE_SIZE_BYTES) {
                Files.write(targetPath, baos.toByteArray());
                return;
            }
            double[] scales = {0.9, 0.8, 0.7, 0.6};
            double[] qualities = {0.9, 0.8, 0.7, 0.6};
            for (double scale : scales) {
                for (double quality : qualities) {
                    baos.reset();
                    int width = (int) (inputImage.getWidth() * scale);
                    int height = (int) (inputImage.getHeight() * scale);
                    BufferedImage scaledImage = Scalr.resize(inputImage, Scalr.Method.QUALITY, width, height);
                    ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();
                    try {
                        ImageWriteParam param = writer.getDefaultWriteParam();
                        if (param.canWriteCompressed()) {
                            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                            param.setCompressionQuality((float) quality);
                        }
                        writer.setOutput(ImageIO.createImageOutputStream(baos));
                        writer.write(null, new IIOImage(scaledImage, null, null), param);
                        if (baos.size() <= MAX_IMAGE_SIZE_BYTES) {
                            Files.write(targetPath, baos.toByteArray());
                            return;
                        }
                    } finally {
                        writer.dispose();
                    }
                }
            }
            Files.write(targetPath, baos.toByteArray());
        }
    }

    private String getImageFormat(byte[] imageData) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageData));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (readers.hasNext()) {
            ImageReader reader = readers.next();
            try {
                return reader.getFormatName();
            } finally {
                reader.dispose();
            }
        }
        return null;
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

    private String addSuffixToFileName(String fileName, int count) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "_" + count + extension;
        }
        return fileName + "_" + count;
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

    @Override
    public List<File> searchFileByName(String searchInfo) {
        return lambdaQuery()
                .like(File::getFileName, searchInfo)
                .list();
    }


}
