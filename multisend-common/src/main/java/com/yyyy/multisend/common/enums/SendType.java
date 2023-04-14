package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author isADuckA
 * @Date 2023/4/10 17:08
 */
@Getter
@ToString
@AllArgsConstructor
public enum SendType {
    SEND(111,"发送消息"),
    ROLLOBACK(222,"撤回");



    private final Integer code;

    private final String desc;
}
