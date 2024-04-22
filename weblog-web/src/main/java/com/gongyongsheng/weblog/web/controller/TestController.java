package com.gongyongsheng.weblog.web.controller;

import com.gongyongsheng.weblog.common.aspect.ApiOperationLog;
import com.gongyongsheng.weblog.common.enums.ResponseCodeEnum;
import com.gongyongsheng.weblog.common.exception.BizException;
import com.gongyongsheng.weblog.common.utils.JsonUtil;
import com.gongyongsheng.weblog.common.utils.Response;
import com.gongyongsheng.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * @Author: gys
 * @Date: 2024/4/10 17:33
 * @Version: v1.0.0
 * @Description: TODO
 **/
@RestController
@Slf4j
@Api(tags="首页模块")
public class TestController {

    @PostMapping("/test1")
    @ApiOperationLog(description = "测试接口-基础版")
    @ApiOperation(value="测试接口-基础版")
    public User test1(@RequestBody User user) {
        return user;
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口-参数校验升级版本1")
    @ApiOperation(value="测试接口-参数校验升级版本1")
    public ResponseEntity<String> test2(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 是否存在校验错误
        if(bindingResult.hasErrors()){
            // 获取校验不通过字段的提示信息
            String errorMsg = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMsg);
        }
        //返回参数
        return ResponseEntity.ok("参数么有任何问题");
    }

    @PostMapping("/test3")
    @ApiOperationLog(description = "测试接口-响应升级版本2")
    @ApiOperation(value="测试接口-响应升级版本2")
    public Response test3(@RequestBody @Validated User user,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Response.fail(errorMsg);
        }
        return Response.success();
    }

    @PostMapping("/test4")
    @ApiOperationLog(description = "测试接口-全局自定义异常处理版本3")
    @ApiOperation(value="测试接口-全局自定义异常处理版本3")
    public Response test4(@RequestBody @Validated User user,BindingResult bindingResult){
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

    @PostMapping("/test5")
    @ApiOperationLog(description = "测试接口-全局其它异常处理版本3")
    @ApiOperation(value = "测试接口-全局其它异常处理版本3")
    public Response test5(@RequestBody @Validated User user,BindingResult bindingResult){
        // 主动定义一个运行时异常，分母不能为零
        int i = 1 / 0;
        return Response.success();
    }

    @PostMapping("/test6")
    @ApiOperationLog(description = "测试接口-参数校验异常自定义处理")
    @ApiOperation(value = "测试接口-参数校验异常自定义处理")
    public Response test6(@RequestBody @Validated User user){
        return Response.success();
    }

    @PostMapping("admin/test7")
    @ApiOperationLog(description = "测试接口-日期格式")
    @ApiOperation(value="测试接口-日期格式")
    public Response test7(@RequestBody @Validated User user){
        //打印入参
        log.info(JsonUtil.toJsonObject(user));

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());
        return Response.success(user);
    }

    @PostMapping("/admin/update")
    @ApiOperationLog(description = "测试接口-更新")
    @ApiOperation(value="测试接口-更新")
    @PreAuthorize("hasRole(ROLE_ADMIN)")
    public Response testUpdate(){
        log.info("更新成功....");
        return Response.success();
    }
}
