package com.gongyongsheng.weblog.common.exception;

import com.gongyongsheng.weblog.common.enums.ResponseCodeEnum;
import com.gongyongsheng.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.rmi.AccessException;
import java.util.Optional;

/**
 * @Author: gys
 * @Date: 2024/4/11 10:04
 * @Version: v1.0.0
 * @Description: 全局异常处理类
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     *捕获自定义业务异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request,BizException e){
        log.warn("{} request fail ,erroCode : {},errorMeassage : {}",request.getRequestURL(),e.getErrorCode(),e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 其它类型异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<Object> handleOtherExcption(HttpServletRequest request,Exception e){
        log.error("{} request error,",request.getRequestURL(),e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 处理方法参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<Object> hanldleMethodArgumentNotValidException(HttpServletRequest request ,MethodArgumentNotValidException e){
        //参数错误异常码
        String errorCode=ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        //获取BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuffer sb = new StringBuffer();

        // 获取校验不通过的字段，并组合错误信息，格式为： email 邮箱格式不正确, 当前值: '123124qq.com';
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors ->{
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append(",当前值：'")
                            .append(error.getRejectedValue())
                            .append("';")
            );
        });

        //错误信息
        String errorMessage=sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorCode,errorMessage);
    }

    @ExceptionHandler({AccessException.class})
    public void  throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("============= 捕获到 AccessDeniedException");
        throw e;
    }
}