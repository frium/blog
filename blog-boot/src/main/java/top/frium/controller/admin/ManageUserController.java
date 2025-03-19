package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.frium.common.R;

import java.util.List;

/**
 * @date 2025-03-18 16:10:34
 * @description
 */
@Api("友链管理")
@RestController
@RequestMapping("/manageUser")
public class ManageUserController {
    @ApiOperation("获取所有用户")
    @PostMapping("/getUsers")
    public R<?> getUsers() {
        return R.success();
    }

}
