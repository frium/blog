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
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.service.UserService;

/**
 *
 * @date 2024-07-29 23:33:26
 * @description
 */
@Api("登录注册接口")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
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

    @ApiOperation("获取邮箱短信")
    @GetMapping("/getEmailSMS")
    public R<?> getEmailSMS(@NotNull String email) {
        userService.getEmailCode(email);
        return R.success();
    }


}
