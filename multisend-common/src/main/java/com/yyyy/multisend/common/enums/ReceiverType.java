package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author isADuckA
 * @Date 2023/4/11 0:10
 * 描述接收者的账号类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum ReceiverType {
    PHONE(1001,"^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$"),
    ALIPAY(1002,"支付宝"),
    WECHAT(1003,"微信");

    private final Integer code;
    private final String ruler;
}
