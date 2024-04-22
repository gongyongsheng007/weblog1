package com.gongyongsheng.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: gys
 * @Date: 2024/4/12 16:56
 * @Version: v1.0.0
 * @Description: Mybatis Plus 配置
 **/
@Configuration
@MapperScan("com.gongyongsheng.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
