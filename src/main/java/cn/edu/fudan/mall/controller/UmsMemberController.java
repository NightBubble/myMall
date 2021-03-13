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

@Api(tags = "UmsMemberController", description="会员登录注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;


    @ApiOperation(value="获取验证码")
    @RequestMapping(value="/getAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone){
       String code = memberService.generateAuthCode(telephone);
       if(code == null){
           return CommonResult.failed("无法获取验证码");
       }
       return CommonResult.success(code);
    }

    @ApiOperation(value="校验验证码")
    @RequestMapping(value = "/validateAuthCode", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult validateAuthCode(@RequestParam String telephone, @RequestParam String authCode){
        if(authCode==null){
            return CommonResult.failed("请输入验证码");
        };
        Boolean ans = memberService.validateAuthCode(telephone,authCode);
        if(ans==false){
            return CommonResult.failed("验证失败");
        }else{
            return CommonResult.success("验证成功");
        }
    }





}
