package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:13
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeType {

    /**
     * 响应的状态码
     */
    OK_200(200,"请求成功"),
    ERROR_400(400,"客户端请求有误"),
    ERROR_500(500,"服务端有误"),

    /**
     * 责任链返回
     */
    DUTY_START(0,"责任链开启"),
    DUTY_SUCCESS(1,"责任链顺利完成"),
    DUTY_ERROR(2,"责任链请求失误"),
    DUTY_ERROR_NULL(3,"责任链为空"),
    DUTY_EEROR_SENDTYPENULL(4,"发送方式为空，即不知道是发送还是撤销"),
    DUTY_ERROR_MSGTASKNULL(5,"MsgTask的值为null");





    /**
     * 状态码
     */
    private  final Integer code;

    private final String desc;



}
