package top.frium.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.context.BaseContext;
import top.frium.mapper.UserMapper;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.PageDTO;
import top.frium.service.UserService;

/**
 *
 * @date 2024-06-20 20:14:48
 * @description
 */
@Validated
@RestController("AdminController")
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('admin')")
public class AdminController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public R<?> login(@RequestBody LoginEmailDTO loginEmailDTO){
        userService.loginByEmail(loginEmailDTO);
        return R.success();
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }


    @ApiOperation("分页查询用户")
    @PostMapping("/findAllUsers")
    public R<?> findAllUsers(@RequestBody PageDTO pageDTO){
     return R.success( userMapper.selectList(null));
    }

    @ApiOperation("通过id删除用户")
    @PostMapping("/deleteUserById")
    public R<?> deleteUserById(String id){
        return R.success(userMapper.deleteById(id));
    }
}
