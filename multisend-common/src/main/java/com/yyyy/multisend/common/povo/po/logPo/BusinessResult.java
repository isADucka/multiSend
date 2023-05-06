package com.yyyy.multisend.common.povo.po.logPo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author isADuckA
 * @Date 2023/5/5 17:49
 * 业务执行结果
 */
@AllArgsConstructor
@Getter
public enum  BusinessResult {
    SUCCESS("业务执行成功"),
    FAIL("执行失败"),
    SUCCESS_SEDN_MQ("成功发送消息到消息中转站"),
    FAILT_SEND("消息下发失败"),
    BUSINESS_DEDUPLICATION("消息去重");



    /**
     * 业务执行结果
     */
    private  final String result;
}
