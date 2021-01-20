package cn.edu.fudan.mall.service;

import cn.edu.fudan.mall.mbg.model.UmsAdmin;
import cn.edu.fudan.mall.mbg.model.UmsPermission;

import java.util.List;

/**
 * 后台管理Service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获得管理员
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     * @param umsAdmin
     * @return
     */
    UmsAdmin register(UmsAdmin umsAdmin);

    /**
     * 登录功能
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 获取用户权限
     * @param umsAdminId
     * @return
     */
    List<UmsPermission> getPermissionList(Long umsAdminId);
}
