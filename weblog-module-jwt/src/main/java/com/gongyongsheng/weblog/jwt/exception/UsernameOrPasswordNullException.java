package com.gongyongsheng.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: gys
 * @Date: 2024/4/12 18:21
 * @Version: v1.0.0
 * @Description: 校验用户名或密码不为空异常处理
 **/
public class UsernameOrPasswordNullException extends AuthenticationException {

  public UsernameOrPasswordNullException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public UsernameOrPasswordNullException(String msg) {
    super(msg);
  }
}
