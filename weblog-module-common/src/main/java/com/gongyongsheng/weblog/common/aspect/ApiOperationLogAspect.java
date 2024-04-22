package com.gongyongsheng.weblog.common.aspect;

import com.gongyongsheng.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: gys
 * @Date: 2024/4/10 16:57
 * @Version: v1.0.0
 * @Description: 日志切面处理类
 **/
@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {

    /*该函数是一个切面编程的切点方法，使用了@Pointcut注解来定义一个切点，
    其作用是匹配被@annotation(com.gongyongsheng.weblog.common.aspect.ApiOperationLog)注解标记的方法
        。简单来说，这个函数的作用是找出所有被ApiOperationLog注解标记的方法。*/
    @Pointcut("@annotation(com.gongyongsheng.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog(){}

    /**
     * 环绕
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            //请求开始时间
            long startTime = System.currentTimeMillis();

            //MDC
            MDC.put("traceId", UUID.randomUUID().toString());

            //获取被请求的类和方法
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            //请求入参
            Object[] args = joinPoint.getArgs();

            //入参转换JSON串
            String argsJsonStr= Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(","));

            //功能描述信息
            String description = getApiOperationLogDescription(joinPoint);

            //打印请求新相关参数
            log.info("请求类名:{},请求方法名:{},请求入参:{},功能描述:{}",className,methodName,argsJsonStr,description);

            //执行切点方法
            Object result = joinPoint.proceed();

            //执行耗时
            long executionTime = System.currentTimeMillis()-startTime;

            //打印出参相关信息
            log.info("请求结束:{},出参:{},请求耗时:{}",description,JsonUtil.toJsonObject(result),executionTime);

            return result;
        }finally {
            MDC.clear();
        }
    }

    /**
     *
     * 获取注解描述信息
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint){
       //1.从ProceedingJoinPoint中用于获取当前执行方法的签名信息，包含方法名/返回类型/参数类型等,MethodSignature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //2.使用MethodSignature获取当前被注解的方法
        Method method = methodSignature.getMethod();

        //3.从Method中获取ApiOperationLog注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        //4.从ApiOperationLog注解中获取描述的属性
        return apiOperationLog.description();

    }


    /**
     * 转JSON串
     * @param args
     * @return
     */
    private Function<Object,String> toJsonStr(){
        return args -> JsonUtil.toJsonObject(args);
    }
}
