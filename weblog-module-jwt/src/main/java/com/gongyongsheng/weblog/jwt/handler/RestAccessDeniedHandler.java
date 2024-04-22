package com.gongyongsheng.weblog.jwt.handler;

import com.gongyongsheng.weblog.common.enums.ResponseCodeEnum;
import com.gongyongsheng.weblog.common.utils.Response;
import com.gongyongsheng.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: gys
 * @Date: 2024/4/15 16:33
 * @Version: v1.0.0
 * @Description: 处理当用户登录成功时，访问受保护的资源，但是权限不够的情况
 **/
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException);

        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.FORBIDDEN));
    }

}
