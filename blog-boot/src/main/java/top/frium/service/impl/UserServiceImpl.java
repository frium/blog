package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.context.RabbitMQConstant;
import top.frium.mapper.UserMapper;
import top.frium.pojo.LoginUser;
import top.frium.pojo.dto.EmailDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.RegisterEmailDTO;
import top.frium.pojo.entity.User;
import top.frium.pojo.vo.LoginVO;
import top.frium.pojo.vo.UserInfoVO;
import top.frium.service.UserService;
import top.frium.uitls.JwtUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static top.frium.context.CommonConstant.*;


/**
 * @date 2024-07-29 23:30:21
 * @description
 */
@SuppressWarnings("all")
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
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void logout() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUser.getUser().getId() != null) redisTemplate.delete(LOGIN_USER + loginUser.getUser().getId());
    }

    @Override
    public void getEmailCode(String email) {
        Long expire = redisTemplate.getExpire(email, TimeUnit.MINUTES);
        if (expire != null && expire >= 4) throw new MyException("获取验证码过于频繁!", 426);
        rabbitTemplate.convertAndSend(RabbitMQConstant.EMAIL_QUEUE, email);//异步发送邮箱验证码
    }

    @Override
    public UserInfoVO getUserInfo() {
        User user = null;
        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = getById(loginUser.getUser().getId());
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        return userInfoVO;
    }

    @Override
    public void updateEmail(EmailDTO emailDTO) {
        if (emailDTO.getEmail() == null) throw new MyException(StatusCodeEnum.FAIL);
        Long userId;
        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = loginUser.getUser().getId();
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        Object o = redisTemplate.opsForValue().get(emailDTO.getEmail());
        if (o == null || !emailDTO.getVerify().equals(o.toString())) {
            throw new MyException(StatusCodeEnum.ERROR_VERIFY);
        }
        User u = lambdaQuery().eq(User::getEmail, emailDTO.getEmail()).one();
        if (u != null) throw new MyException(StatusCodeEnum.USER_EXIST);
        lambdaUpdate().eq(User::getId, userId).set(User::getEmail, emailDTO.getEmail()).update();
    }

    @Override
    public void updateUsername(String username) {
        Long userId;
        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = loginUser.getUser().getId();
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.NOT_LOGIN);
        }
        lambdaUpdate().eq(User::getId, userId).set(User::getUsername, username).update();
    }

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
        user.setCreateTime(LocalDateTime.now().format(DATA_TIME_PATTERN));
        user.setUsername("用户" + Math.round(100000 + Math.random() * 900000));
        save(user);
        userMapper.addUserPermission(user.getId());
    }

    @Override
    public LoginVO loginByEmail(LoginEmailDTO loginEmailDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginEmailDTO.getEmail(), loginEmailDTO.getPassword());
        Authentication authenticate;
        //判断认证是否通过
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        //存token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisTemplate.opsForValue().set(LOGIN_USER + id, loginUser, 7, TimeUnit.DAYS);
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ID, id);
        return new LoginVO(JwtUtil.createToken(key, ttl, claims), loginUser.getPermission().get(0));
    }
}
