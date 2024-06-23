package top.frium.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.R;
import top.frium.utils.FileUtil;

/**
 *
 * @date 2024-06-20 16:41:38
 * @description
 */
@Validated
@RestController("ImageController")
@RequestMapping("/image")
public class ImageController {
    @Autowired
    FileUtil fileUtil;

    @ApiOperation("上传图片")
    @PostMapping("/admin/loadImage")
    @PreAuthorize("hasAnyAuthority('admin')")
    public R<String> loadImage(@RequestParam("image") MultipartFile image) {
        //将图片写入本地并返回地址
        return R.success(fileUtil.saveUploadedFile(image));
    }

}
