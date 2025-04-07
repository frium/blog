package top.frium.controller.user;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.frium.common.R;
import top.frium.pojo.vo.VisitVO;

/**
 * @date 2024-11-18 12:22:30
 * @description
 */
@Api("获取UV和PV")
@RestController
@RequestMapping("/visit")
public class IpController {
    @Autowired
    RedisTemplate<Object, Object> redisTemplate;
    @Value("${visit.uv}")
    String HLL_KEY;
    @Value("${visit.pv}")
    String PV_KEY;
    @RequestMapping("/getUVAndPV")
    public R<?> getUV() {
        Object pvValue = redisTemplate.opsForValue().get(PV_KEY);
        return R.success(new VisitVO(redisTemplate.opsForHyperLogLog().size(HLL_KEY), pvValue != null ? ((Number) pvValue).longValue() : 0L));
    }


}
