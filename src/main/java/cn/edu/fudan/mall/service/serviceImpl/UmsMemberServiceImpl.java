package cn.edu.fudan.mall.service.serviceImpl;

import cn.edu.fudan.mall.common.api.CommonResult;
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
    private Long AUTH_CODE_EXPIRE_SECONDES;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 6;i++) {
            sb.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE + telephone, AUTH_CODE_EXPIRE_SECONDES);
        return CommonResult.success(sb.toString(),"获取验证码成功");
    }

    @Override
    public CommonResult vertifyAuthCode(String telephone, String authCode) {
        if(authCode.isEmpty()){
            return CommonResult.failed("请输入验证码");
        }
        String realCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        if(realCode.equals(authCode)){
            return CommonResult.success("校验正确");
        }else{
            return CommonResult.failed("校验失败");
        }
    }
}
