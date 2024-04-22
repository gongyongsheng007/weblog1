package com.gongyongsheng.weblog.admin.config;

import com.gongyongsheng.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.gongyongsheng.weblog.jwt.filter.TokenAuthenticationFilter;
import com.gongyongsheng.weblog.jwt.handler.RestAccessDeniedHandler;
import com.gongyongsheng.weblog.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: gys
 * @Date: 2024/4/15 10:38
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

  @Autowired
  private RestAuthenticationEntryPoint authEntryPoint;
  @Autowired
  private RestAccessDeniedHandler deniedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable(). // 禁用 csrf
            formLogin().disable() // 禁用表单登录
            .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
        .and()
            .authorizeHttpRequests()
            .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
            .anyRequest().permitAll() // 其他都需要放行，无需认证
        .and()
            .httpBasic().authenticationEntryPoint(authEntryPoint) // 处理用户未登录访问受保护的资源的情况
        .and()
            .exceptionHandling().accessDeniedHandler(deniedHandler) // 处理登录成功后访问受保护的资源，但是权限不够的情况
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离，无需创建会话
        .and()
            .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) ;// 将 Token 校验过滤器添加到用户认证过滤器之前
  }

  /**
   * Token 校验过滤器
   * @return
   */
  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }
}
