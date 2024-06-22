package top.frium.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.frium.common.R;
import top.frium.context.BaseContext;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.LoginPhoneDTO;
import top.frium.service.UserService;

/**
 *
 * @date 2024-06-19 18:33:02
 * @description
 */
@Validated
@RestController("UserController")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("手机号登录")
    @PostMapping("/loginByPhone")
    public R<?> loginByPhone(@RequestBody LoginPhoneDTO loginPhoneDTO) {
        return null;
    }

    @ApiOperation("获取手机短信")
    @GetMapping("/getPhoneSMS")
    public R<?>getPhoneSMS(){
        return null;
    }

    @ApiOperation("邮箱登录")
    @PostMapping("/loginByEmail")
    public R<?> loginByEmail(@RequestBody LoginEmailDTO loginEmailDTO) {
        userService.loginByEmail(loginEmailDTO);
        return null;
    }

    @ApiOperation("获取邮箱短信")
    @GetMapping("/getEmailSMS")
    public R<?>getEmailSMS(String email){
        userService.getEmailSMS(email);
        return R.success() ;
    }

    @ApiOperation("评论")
    @PostMapping("/userComment")
    public R<?> userComment(@RequestBody  CommentDTO commentDTO){
        userService.comment(commentDTO);
        return R.success();
    }

    @ApiOperation("修改信息")
    @PostMapping("/updateInfo")
    public R<?> updateInfo(){
        return null;
    }


    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<?> logout() {
        BaseContext.removeCurrentId();
        return R.success();
    }
}
