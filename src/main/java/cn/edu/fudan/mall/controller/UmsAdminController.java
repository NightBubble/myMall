package cn.edu.fudan.mall.controller;

import cn.edu.fudan.mall.common.api.CommonResult;
import cn.edu.fudan.mall.dto.UmsAdminLoginParam;
import cn.edu.fudan.mall.mbg.model.UmsAdmin;
import cn.edu.fudan.mall.mbg.model.UmsPermission;
import cn.edu.fudan.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台程序管理
 */
@Controller
@Api(tags = "UmsAdminController", description="后台权限管理")
@RequestMapping("/admin")
public class UmsAdminController {
    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result){
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if(umsAdmin == null){
            return CommonResult.failed("注册失败");
        }else{
            return CommonResult.success(umsAdmin, "注册成功");
        }
    }

    @ApiOperation(value = "用户登录并返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam loginParam, BindingResult result){
        String token = umsAdminService.login(loginParam.getUsername(), loginParam.getPassword());
        if(token == null){
            return CommonResult.validFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取用户权限")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.POST)
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable("id")Long id){
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(id);
        return CommonResult.success(permissionList);
    }

}
