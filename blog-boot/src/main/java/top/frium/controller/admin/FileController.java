package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.R;
import top.frium.service.FileService;

import java.util.List;

/**
 * @date 2025-03-13 21:15:06
 * @description
 */
@Api("文件管理")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    public R<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return R.success(fileService.uploadFile(file));
    }

    @ApiOperation("获取所有文件")
    @GetMapping("/getAllFiles")
    public R<?> uploadFiles() {
        return R.success(fileService.getAllFiles());
    }

    @ApiOperation("通过id数组删除文件")
    @PostMapping("/deleteFile")
    public R<?> deleteFile(@RequestBody List<Long> fileIds) {
        fileService.deleteFile(fileIds);
        return R.success();
    }
}
