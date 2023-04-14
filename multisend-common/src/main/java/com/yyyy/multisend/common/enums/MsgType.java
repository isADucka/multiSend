package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author isADuckA
 * @Date 2023/4/10 10:32
 */
@Getter
@ToString
@AllArgsConstructor
public enum  MsgType {

    VERIFY(101,"验证码类"),
    MARKETING(102,"营销类"),
    NOTICE(103,"通知类");



    /**
     * 消息类型
     */
    private final Integer type;

    /**
     * 描述
     */
    private final String desc;
}
