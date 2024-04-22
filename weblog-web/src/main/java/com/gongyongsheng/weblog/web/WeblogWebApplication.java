package com.gongyongsheng.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: gys
 * @Date: 2024/4/10 15:44
 * @Version: v1.0.0
 * @Description: 启动类
 **/
@SpringBootApplication
@ComponentScan({"com.gongyongsheng.weblog.*"}) // 多模块项目中，必需手动指定扫描 com.quanxiaoha.weblog 包下面的所有类
public class WeblogWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeblogWebApplication.class, args);
    }
}
