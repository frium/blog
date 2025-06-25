package top.frium.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.frium.common.MyException;
import top.frium.common.R;
import top.frium.common.StatusCodeEnum;
import top.frium.pojo.dto.UserDTO;
import top.frium.pojo.entity.User;
import top.frium.service.impl.UserServiceImpl;

import java.util.List;

/**
 * @date 2025-03-18 16:10:34
 * @description
 */
@Api("用户管理")
@RestController
@RequestMapping("/manageUser")
@PreAuthorize("hasAuthority('superAdmin')")
public class ManageUserController {
    @Autowired
    UserServiceImpl userService;


    @ApiOperation("获取所有用户")
    @GetMapping("/getUsers")
    public R<?> getUsers() {
        return R.success(userService.getUsers());
    }

    @ApiOperation("获取用户数量")
    @GetMapping("/getUserNum")
    public R<?> getUserNum() {
        return R.success(userService.count());
    }


    @ApiOperation("删除用户")
    @PostMapping("deleteUsers")
    public R<?> deleteUsers(@RequestBody List<Long> userIds) {
        userService.deleteUsers(userIds);
        return R.success();
    }

    @ApiOperation("修改用户信息")
    @PostMapping("updateUserInfo")
    public R<?> updateUserInfo(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return R.success();
    }

    @ApiOperation("用户名唯一性校验")
    @GetMapping("verifyUsername")
    public R<?> verifyUsername(String username, Long userId) {
        boolean exists = userService.lambdaQuery().eq(User::getUsername, username).ne(userId != null, User::getId, userId).exists();
        if (exists) throw new MyException(StatusCodeEnum.USER_NAME_EXIST);
        return R.success();
    }

    @ApiOperation("邮箱唯一性校验")
    @GetMapping("verifyEmail")
    public R<?> verifyEmail(String email, @RequestParam(required = false) Long userId) {
        boolean exists = userService.lambdaQuery().eq(User::getEmail, email).ne(userId != null, User::getId, userId).exists();
        if (exists) throw new MyException(StatusCodeEnum.USER_EXIST);
        return R.success();
    }

    @ApiOperation("创建用户")
    @PostMapping("createUser")
    public R<?> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return R.success();
    }

    @ApiOperation("根据名称搜索用户")
    @PostMapping("/searchUserByName")
    public R<?> searchUserByName(String searchInfo) {
        return R.success(userService.searchUserByName(searchInfo));
    }
}
