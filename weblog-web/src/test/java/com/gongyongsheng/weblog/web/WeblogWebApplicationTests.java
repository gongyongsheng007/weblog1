package com.gongyongsheng.weblog.web;

import com.gongyongsheng.weblog.common.domain.doc.UserDO;
import com.gongyongsheng.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author: gys
 * @Date: 2024/4/10 15:46
 * @Version: v1.0.0
 * @Description: 测试类
 **/
@SpringBootTest
@Slf4j
public class WeblogWebApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){
        //编写单元测试
    }

    @Test
    void testLog() {
        log.info("这是一行 Info 级别日志");
        log.warn("这是一行 Warn 级别日志");
        log.error("这是一行 Error 级别日志");

        // 占位符
        String author = "龚勇胜";
        log.info("这是一行带有占位符日志，作者：{}", author);
    }

    @Test
    void insertTest(){
        UserDO userDO = UserDO.builder()
                .username("gongyongsheng007")
                .password("a888888")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();
        userMapper.insert(userDO);
    }
}
