package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.UserMapper;
import top.frium.pojo.LoginUser;
import top.frium.pojo.dto.ForgetPasswordDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.pojo.entity.User;
import top.frium.service.UserService;
import top.frium.uitls.JwtUtil;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static top.frium.context.CommonConstant.LOGIN_USER;
import static top.frium.context.CommonConstant.USER_ID;


/**
 *
 * @date 2024-07-29 23:30:21
 * @description
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    AuthenticationManager authenticationManager;
    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    Long ttl;
    @Autowired
    UserMapper userMapper;


    @Override
    public void registerByEmail(RegisterEmailDTO registerEmailDTO) {
        //在redis中检查是否数据匹配
        Object o = redisTemplate.opsForValue().get(registerEmailDTO.getEmail());
        if (o == null || !registerEmailDTO.getVerify().equals(o.toString())) {
            throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        }
        //判断是否重复注册
        User u = lambdaQuery().eq(User::getEmail, registerEmailDTO.getEmail()).one();
        if (u != null) throw new MyException(StatusCodeEnum.USER_EXIST);
        //加密密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        registerEmailDTO.setPassword(bCryptPasswordEncoder.encode(registerEmailDTO.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(registerEmailDTO, user);
        save(user);
        userMapper.addUserPermission(user.getId());
    }

    @Override
    public String loginByEmail(LoginEmailDTO loginEmailDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginEmailDTO.getEmail(), loginEmailDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断认证是否通过
        if (Objects.isNull(authenticate)) {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        } else {
            //存token
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            Long id = loginUser.getUser().getId();
            redisTemplate.opsForValue().set(LOGIN_USER + id, loginUser,7, TimeUnit.DAYS);
            Map<String, Object> claims = new HashMap<>();
            claims.put(USER_ID, id);
            return JwtUtil.createToken(key, ttl, claims);
        }
    }

    @Override
    public void forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        Object o = redisTemplate.opsForValue().get(forgetPasswordDTO.getEmail());
        if (o == null || !forgetPasswordDTO.getVerify().equals(o.toString())) {
            throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        }
        boolean update = lambdaUpdate().eq(User::getEmail, forgetPasswordDTO.getEmail())
                .set(User::getPassword, forgetPasswordDTO.getNewPassword()).update();
        if(!update) throw new MyException(StatusCodeEnum.USER_NOT_EXIST);
    }
}
