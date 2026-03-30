package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.frium.common.MyException;
import top.frium.common.R;
import top.frium.pojo.LoginUser;
import top.frium.service.FileService;

import java.util.List;
import java.util.concurrent.TimeUnit;



/**
 * @date 2025-03-13 21:15:06
 * @description
 */
@Api("文件管理")
@RestController
@RequestMapping("/file")
@PreAuthorize("hasAuthority('superAdmin')")

public class FileController {
    @Autowired
    FileService fileService;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation("上传文件")
    @PreAuthorize("permitAll()")
    @PostMapping("/uploadFile")
    public R<?> uploadFile(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // superAdmin无限制
        boolean isSuperAdmin = loginUser.getPermission().stream()
                .anyMatch("superAdmin"::equals);
        if (!isSuperAdmin) {
            String rateLimitKey = "uploadRateLimit:" + loginUser.getUser().getId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey(rateLimitKey))) {
                throw new MyException("今日上传次数已达上限", 429);
            }
        }

        String result = fileService.uploadFile(file);

        // 非superAdmin上传成功后设置限流key，TTL 24小时
        if (!isSuperAdmin) {
            String rateLimitKey = "uploadRateLimit:" + loginUser.getUser().getId();
            redisTemplate.opsForValue().set(rateLimitKey, "1", 24, TimeUnit.HOURS);
        }

        return R.success(result);
    }

    @ApiOperation("获取所有文件")
    @PreAuthorize("permitAll()")
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
    @ApiOperation("根据名称搜索文件")
    @PostMapping("/searchFileByName")
    public R<?> searchFileByName( String searchInfo) {
        return R.success(fileService.searchFileByName(searchInfo));
    }
}
