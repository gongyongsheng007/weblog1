package com.gongyongsheng.weblog.common.domain.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: gys
 * @Date: 2024/4/12 16:59
 * @Version: v1.0.0
 * @Description: 用户实体类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user")
public class UserDO {

  @TableId(type = IdType.AUTO)
  private Long id;

  private String username;

  private String password;

  private Date createTime;

  private Date updateTime;

  private Boolean isDeleted;
}
