package cn.edu.fudan.mall.controller;

import cn.edu.fudan.mall.common.api.CommonResult;
import cn.edu.fudan.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "UmsMemberController")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService umsMemberService;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam("telephone")String telephone){
        System.out.println(telephone);
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("更新密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@RequestParam(value = "telephone") String telephone,
                                       @RequestParam(value = "authCode") String authCode){
        Boolean isValid =  umsMemberService.vertifyAuthCode(telephone,authCode).isSuccess();
        return umsMemberService.vertifyAuthCode(telephone, authCode);
    }




}
