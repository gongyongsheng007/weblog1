package com.gongyongsheng.weblog.common.exception;

/**
 * @Author: gys
 * @Date: 2024/4/11 9:48
 * @Version: v1.0.0
 * @Description: 通用异常处理
 **/
public interface BaseExceptionInterface {

    //获取异常码
    String getErrorCode();

    //获取异常信息
    String getErrorMessage();
}
