package cn.edu.fudan.mall.service.serviceImpl;

import cn.edu.fudan.mall.service.RedisService;
import cn.edu.fudan.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public String generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        String authCode = sb.toString();
        String redisIndex = REDIS_KEY_PREFIX_AUTH_CODE + telephone;

        //验证码存储到redis
        redisService.set(redisIndex, authCode);
        redisService.expire(redisIndex, AUTH_CODE_EXPIRE_SECONDS);
        return sb.toString();
    }

    @Override
    public Boolean validateAuthCode(String telephone, String authCode) {
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        boolean result = authCode.equals(realAuthCode);
        return result;
    }

}
