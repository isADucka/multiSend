package com.yyyy.multisend.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.assertj.core.util.Lists;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/4/10 15:55
 */
@Configuration
public class CommonConfig{

//    /**
//     * FastJson 消息转换器 格式化输出json
//     *
//     * @return
//     */
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        List<MediaType> supportedMediaTypes = Lists.newArrayList();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
//        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
//        return new HttpMessageConverters(fastConverter);
//    }
}
