package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.entity.BlogGlobalInfo;
import top.frium.service.BlogGlobalInfoService;

/**
 * @date 2025-06-03 21:51:41
 * @description
 */
@Api("博客全局参数管理")
@RestController
@RequestMapping("/blogGlobalInfo")
@PreAuthorize("hasAuthority('superAdmin')")
public class BlogGlobalInfoController {
    @Autowired
    BlogGlobalInfoService blogGlobalInfoService;

    @ApiOperation("修改全局参数")
    @PreAuthorize("permitAll()")
    @PostMapping("/updateGlobalInfo")
    public R<?> updateGlobalInfo(@RequestBody BlogGlobalInfo blogGlobalInfo) {
        blogGlobalInfoService.updateBlogGlobalInfo(blogGlobalInfo);
        return R.success();
    }
    @ApiOperation("获取全局参数")
    @PreAuthorize("permitAll()")
    @GetMapping("/getGlobalInfo")
    public R<?> getGlobalInfo() {
        return R.success( blogGlobalInfoService.getGlobalInfo());

    }
}
