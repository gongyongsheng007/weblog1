package com.gongyongsheng.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: gys
 * @Date: 2024/4/10 16:53
 * @Version: v1.0.0
 * @Description: JSON格式转换工具类
 **/
@Slf4j
public class JsonUtil {

  private static final ObjectMapper INSTANCE = new ObjectMapper();

  public static String toJsonObject(Object obj) {
    try {
      return INSTANCE.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return obj.toString();
    }
  }
}
