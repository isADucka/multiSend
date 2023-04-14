package com.yyyy.multisend.common.ssm;


import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author isADuckA
 * @Date 2023/4/10 10:40
 */

@Getter
@Component
public class SsmOfAliyun {

    @Value("${multisend.aliyun.accessKey}")
    private String accessKeyId;
    @Value("${multisend.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${multiSend.aliyun.endpoint}")
    private String endpoint;
    @Value("${multisend.aliyun.singleName}")
    private String singleName;
    @Value("${multisend.aliyun.templateId}")
    private String templateId;
}
