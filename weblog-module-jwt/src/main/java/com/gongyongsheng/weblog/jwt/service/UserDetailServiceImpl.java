package com.gongyongsheng.weblog.jwt.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gongyongsheng.weblog.common.domain.doc.UserDO;
import com.gongyongsheng.weblog.common.domain.doc.UserRoleDO;
import com.gongyongsheng.weblog.common.domain.mapper.UserMapper;
import com.gongyongsheng.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: gys
 * @Date: 2024/4/12 18:05
 * @Version: v1.0.0
 * @Description: 用户服务类
 **/
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);
        //判断用户是否存在
        if(Objects.isNull(userDO)){
            throw new UsernameNotFoundException("用户不存在");
        }

        //用户角色
        List<UserRoleDO> roleDOS = userRoleMapper.selectByUsername(username);

        String[] roleArr=null;

        // 转数组
        if (!CollectionUtils.isEmpty(roleDOS)) {
            List<String> roles = roleDOS.stream().map(p -> p.getRole()).collect(Collectors.toList());
            roleArr = roles.toArray(new String[roles.size()]);
        }

        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }
}
