package top.frium.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.pojo.dto.EmailDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.service.LinkService;
import top.frium.service.UserService;

/**
 * @date 2024-07-29 23:33:26
 * @description
 */
@Api("用户接口")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    LinkService linkService;
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @ApiOperation("邮箱注册")
    @PostMapping("/registerByEmail")
    public R<?> registerByEmail(@Valid @RequestBody RegisterEmailDTO registerEmailDTO) {
        userService.registerByEmail(registerEmailDTO);
        return R.success();
    }

    @ApiOperation("邮箱登录")
    @PostMapping("/loginByEmail")
    public R<?> loginByEmail(@Valid @RequestBody LoginEmailDTO loginEmailDTO) {
        return R.success(userService.loginByEmail(loginEmailDTO));
    }

    @ApiOperation("修改邮箱")
    @PostMapping("/updateEmail")
    public R<?> updateEmail(@RequestBody EmailDTO emailDTO) {
        userService.updateEmail(emailDTO);
        return R.success();
    }

    @ApiOperation("修改用户名")
    @PostMapping("/updateUsername")
    public R<?> updateUsername(String username) {
        userService.updateUsername(username);
        return R.success();
    }

    @ApiOperation("修改用户头像")
    @PostMapping("/updateAvatar")
    public R<?> updateAvatar(String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return R.success();
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<?> logout() {
        userService.logout();
        return R.success();
    }

    @ApiOperation("获取邮箱短信")
    @GetMapping("/getEmailSMS")
    public R<?> getEmailSMS(@NotNull String email) {
        userService.getEmailCode(email);
        return R.success();
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/getUserInfo")
    public R<?> getUserInfo() {
        return R.success(userService.getUserInfo());
    }


    @ApiOperation("获取友链")
    @GetMapping("/getLinks")
    public R<?> getLinks() {
        return R.success(linkService.getLinkVOs());
    }
}
