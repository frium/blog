package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.context.BaseContext;
import top.frium.mapper.CommentMapper;
import top.frium.mapper.UserMapper;
import top.frium.pojo.dto.CommentDTO;
import top.frium.pojo.dto.LoginEmailDTO;
import top.frium.pojo.dto.LoginPhoneDTO;
import top.frium.pojo.entity.Comment;
import top.frium.pojo.entity.User;
import top.frium.service.UserService;
import top.frium.utils.EmailUtil;
import top.frium.utils.JwtUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static top.frium.utils.IpUtil.getIpAddress;
import static top.frium.utils.IpUtil.getIpSource;

/**
 *
 * @date 2024-06-19 18:58:25
 * @description
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.key}")
    String key;
    @Value("${jwt.ttl}")
    Long ttl;


    @Override
    public void loginByPhone(LoginPhoneDTO loginDTO) {

    }

    @Override
    public void getPhoneSMS() {

    }

    @Override
    public void loginByEmail(LoginEmailDTO loginEmailDTO) {
        //在redis中检查是否数据匹配
        Object o = redisTemplate.opsForValue().get(loginEmailDTO.getEmail());
        if(o==null||!loginEmailDTO.getPassword().equals(o.toString())){
            throw new MyException(StatusCodeEnum.LOGIN_FAIL);
        }
        //获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        //判断是否重复身份,重复只更新登录时间
        User u = lambdaQuery().eq(User::getEmail, loginEmailDTO.getEmail()).one();
        long id;
        if(u!=null) {
            lambdaUpdate().eq(User::getId,u.getId()).set(User::getLoginTime,formattedDateTime).update();
            id=u.getId();
        }else {
            User user=new User();
            user.setEmail(loginEmailDTO.getEmail());
            user.setLoginTime(formattedDateTime);
            String ipAddress = getIpAddress(request);
            user.setIp(ipAddress);
            user.setAddress(getIpSource(ipAddress));
            Random random = new Random();
            long number = (long) (100000000000L + random.nextDouble() * 900000000000L);
            user.setUsername("用户"+number);
            save(user);
            id=user.getId();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", id);
        response.setHeader("Authorization", JwtUtil.createToken(key, ttl, claims));//设置token到请求头中
    }

    @Override
    public void getEmailSMS(String email) {
       emailUtil.sendVerificationEmail(email);
    }

    @Override
    public void comment(CommentDTO commentDTO) {
        Long currentId = BaseContext.getCurrentId();
        User user = lambdaQuery().eq(User::getId, currentId).one();
        if(user==null) throw  new MyException(StatusCodeEnum.NOT_LOGIN);
        Comment comment=new Comment();
        BeanUtils.copyProperties(user,comment);
        BeanUtils.copyProperties(commentDTO,comment);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        comment.setTime(currentDateTime.format(formatter));
        commentMapper.insert(comment);
    }

}
