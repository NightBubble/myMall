package cn.edu.fudan.mall.service.serviceImpl;

import cn.edu.fudan.mall.common.utils.JwtTokenUtil;
import cn.edu.fudan.mall.mbg.mapper.UmsAdminMapper;
import cn.edu.fudan.mall.mbg.mapper.UmsPermissionMapper;
import cn.edu.fudan.mall.mbg.model.UmsAdmin;
import cn.edu.fudan.mall.mbg.model.UmsAdminExample;
import cn.edu.fudan.mall.mbg.model.UmsPermission;
import cn.edu.fudan.mall.mbg.model.UmsPermissionExample;
import cn.edu.fudan.mall.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if(umsAdminList != null && umsAdminList.size()>0){
            return umsAdminList.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam,  umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同的用户
        UmsAdmin equalWithName = getAdminByUsername(umsAdmin.getUsername());
        if(equalWithName != null){
            return null;
        }
        //进行加密工作
        umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
        umsAdminMapper.insertSelective(umsAdmin);
        return umsAdmin;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (UsernameNotFoundException e) {
            logger.warn("登录异常:{}",e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long umsAdminId) {
        UmsPermissionExample example = new UmsPermissionExample();
        example.createCriteria().andIdEqualTo(umsAdminId);
        List<UmsPermission> umsPermissionList = umsPermissionMapper.selectByExample(example);
        return umsPermissionList;
    }
}
