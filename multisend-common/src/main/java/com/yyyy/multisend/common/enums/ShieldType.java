package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author isADuckA
 * @Date 2023/5/7 10:37
 * 屏蔽服务 0为夜间不屏蔽，1为夜间屏蔽，2为次日发送
 */
@AllArgsConstructor
@Getter
public enum  ShieldType {

    SHIELD(0,"夜间不屏蔽"),
    SHILE_NOT_SEND(1,"夜间屏蔽，隔天不发送"),
    SHILED_TOMORROW_SEND(2,"夜间屏蔽，隔天发送");

    private final Integer code;
    private final String desc;
}
