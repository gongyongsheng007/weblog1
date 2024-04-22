package com.gongyongsheng.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gongyongsheng.weblog.common.domain.doc.UserRoleDO;

import java.util.List;

/**
 * @Author: gys
 * @Date: 2024/4/19 16:29
 * @Version: v1.0.0
 * @Description: 用户角色消息操作接口
 **/
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {

  /**
   * 根据用户名查询
   * @param username
   * @return
   */
  default List<UserRoleDO> selectByUsername(String username) {
    LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserRoleDO::getUsername, username);

    return selectList(wrapper);
  }
}
