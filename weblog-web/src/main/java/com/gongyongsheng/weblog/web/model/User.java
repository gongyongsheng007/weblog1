package com.gongyongsheng.weblog.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @Author: gys
 * @Date: 2024/4/10 17:32
 * @Version: v1.0.0
 * @Description: 用户信息
 **/
@Data
@ApiModel(value = "用户实体类")
public class User {

    //姓名
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    //性别
    @NotNull(message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Integer sex;

    //年龄
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于等于18")
    @Max(value = 60, message = "年龄必须小于等于60")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    //邮箱
    @NotNull(message="邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "邮箱")
    private String email;

    // 创建时间
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    // 更新日期
    @ApiModelProperty(value="更新日期")
    private LocalDate updateDate;

    // 时间
    @ApiModelProperty(value="时间")
    private LocalTime time;
}
