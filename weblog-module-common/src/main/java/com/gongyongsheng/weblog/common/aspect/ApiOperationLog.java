package com.gongyongsheng.weblog.common.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Author: gys
 * @Date: 2024/4/10 16:46
 * @Version: v1.0.0
 * @Description: 实现自动添加日志
 **/
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)//运行时生效
@Target(java.lang.annotation.ElementType.METHOD)//方法上使用
@Documented//生成文档时包含注解相关信息
public @interface ApiOperationLog {

    /**
     * API 功能描述
     *
     * @return
     */
    String description() default "";
}
