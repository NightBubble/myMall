package cn.edu.fudan.mall.service;

import cn.edu.fudan.mall.common.api.CommonResult;

public interface UmsMemberService {
    /**
     * 生成验证码
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 校验验证码
     */
    CommonResult vertifyAuthCode(String telephone, String authCode);
}
