package com.gongyongsheng.weblog.common.domain.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: gys
 * @Date: 2024/4/19 11:45
 * @Version: v1.0.0
 * @Description: 用户角色实体类
 **/
@Data
@Builder
@TableName("t_user_role")
public class UserRoleDO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String role;
    private Date createTime;
}
