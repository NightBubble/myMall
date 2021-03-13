package cn.edu.fudan.mall.service;

public interface UmsMemberService {
    /**
     * 生成验证码
     * @param telephone
     * @return
     */
    String generateAuthCode(String telephone);

    /**
     * 验证码校验
     * @param telephone
     * @param authCode
     * @return
     */
    Boolean validateAuthCode(String telephone, String authCode);
}
