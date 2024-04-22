package com.gongyongsheng.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyongsheng.weblog.common.domain.doc.UserDO;

/**
 * @Author: gys
 * @Date: 2024/4/12 17:02
 * @Version: v1.0.0
 * @Description: 用户信息操作接口
 **/
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO findByUsername(String username){
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername,username);
        return selectOne(wrapper);
    }
}
