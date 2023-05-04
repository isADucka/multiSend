package com.yyyy.multisend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author isADuckA
 * @Date 2023/4/26 15:23
 * 实现去重功能
 */
@AllArgsConstructor
@Getter
public enum DeduplicationType {

    CONTENT_DEDUP(10,"内容去重"),
    PFRE_DEDUP(20,"频次去重");

    /**
     *  去重编码
     */
    private final Integer code;

    /**
     * 去重的类型
     */
    private final String desc;

}
