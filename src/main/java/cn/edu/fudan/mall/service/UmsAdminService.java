package cn.edu.fudan.mall.service;

import cn.edu.fudan.mall.mbg.model.UmsAdmin;
import cn.edu.fudan.mall.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台用户Service
 */
public interface UmsAdminService {
    /**
     * 根据用户获得后台管理员
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册用户
     * @param umsAdminParam
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登录用户
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 获取用户权限
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
