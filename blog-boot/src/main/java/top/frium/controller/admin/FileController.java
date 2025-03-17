package top.frium.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.R;
import top.frium.service.UploadFileService;

/**
 * @date 2025-03-13 21:15:06
 * @description
 */

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    UploadFileService uploadFileService;
    @PostMapping("/uploadFile")
    public R<?> uploadFile(@RequestParam("file") MultipartFile file) {
        uploadFileService.uploadFile(file);
        return R.success();
    }



}
