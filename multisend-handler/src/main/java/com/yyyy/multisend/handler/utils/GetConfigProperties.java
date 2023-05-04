package com.yyyy.multisend.handler.utils;

import cn.hutool.setting.dialect.Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author isADuckA
 * @Date 2023/4/28 13:50
 * 获取本地配置文件，是去重的配置文件
 */
@Service
public class GetConfigProperties {

    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);

    public String getLocalProperties(String key,String defaultValue){
        return props.getProperty(key,defaultValue);
    }
}
