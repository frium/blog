package top.frium.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 文件操作工具类
 */
@Component
@SuppressWarnings("all")
public class FileUtil {
    /**
     * 读取文件内容为二进制数组
     *
     * @param filePath 文件路径
     * @return 文件内容的二进制数组
     * @throws IOException 如果发生 I/O 错误
     */
    public byte[] read(MultipartFile file) {
        byte[] data = null;
        try {
            InputStream in = file.getInputStream();
            data = inputStream2ByteArray(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return data;
    }

    public String convertMultipartFileToString(MultipartFile file) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            try (InputStream inputStream = file.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return stringBuilder.toString();
    }

    /**
     * 流转二进制数组
     *
     * @param in 输入流
     * @return 二进制数组
     * @throws IOException 如果发生 I/O 错误
     */
    static byte[] inputStream2ByteArray(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (true) {
            try {
                if (!((n = in.read(buffer)) != -1)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * 保存文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param content 文件内容的二进制数组
     */
    public void save(String filePath, String fileName, byte[] content) {
        try {
            // 创建文件目录（如果不存在）
            File filedir = new File(filePath);
            if (!filedir.exists()) {
                filedir.mkdirs();
            }
            // 创建文件对象
            File file = new File(filedir, fileName);
            // 创建输出流并写入内容
            OutputStream os = new FileOutputStream(file);
            os.write(content, 0, content.length);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // 捕获文件未找到异常并打印堆栈跟踪
        } catch (IOException e) {
            e.printStackTrace(); // 捕获 I/O 异常并打印堆栈跟踪
        }
    }

    // 保存上传的文件并返回文件路径
    public  String saveUploadedFile(MultipartFile file) {
        // 获取原始文件名
        String originalFileName = file.getOriginalFilename();
        // 获取文件后缀名
        String fileExtension = getFileExtension(originalFileName);
        // 生成随机文件名
        String randomFileName = generateRandomFileName(fileExtension);
        // 创建保存路径
        Path savePath = Paths.get("usr/local/image/", randomFileName);
        // 创建目录如果不存在
        try {
            Files.createDirectories(savePath.getParent());
            File saveFile = savePath.toFile();
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savePath.toString();
    }

    // 获取文件后缀名
    private  String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    // 生成随机文件名
    private  String generateRandomFileName(String fileExtension) {
        String uuid = UUID.randomUUID().toString();
        return uuid + fileExtension;
    }


}
