package com.yyyy.multisend.common.povo.po;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * @author isADuckA
 * @Date 2023/4/23 10:58
 * 处理接口传进来的参数
 */
@Data
@Setter
@Getter
public class MessageParm {
    /**
     * 模板id
     */
    private Long modelId;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 参数信息
     */
    private String content;



}
