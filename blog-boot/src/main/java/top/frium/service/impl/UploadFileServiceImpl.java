package top.frium.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.MyException;
import top.frium.pojo.LoginUser;
import top.frium.service.UploadFileService;
import top.frium.uitls.MQUtil;

import static top.frium.common.StatusCodeEnum.ERROR;

/**
 * @date 2025-03-13 22:25:32
 * @description
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    MQUtil MQUtil;
    @Override
    public void uploadFile(MultipartFile file) {
        String fileName;
        try {
            fileName = MQUtil.sendFileMessage(file);
        } catch (Exception e) {
            throw new MyException(ERROR);
        }
        System.out.println(fileName);
    }
}
