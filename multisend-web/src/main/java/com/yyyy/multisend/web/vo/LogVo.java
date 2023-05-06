package com.yyyy.multisend.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * @author isADuckA
 * @Date 2023/5/4 23:05
 * 记录日志参数，主要是用于请求日志
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogVo {
    /**
     * 这个id应该对应上userId
     */
    @JSONField(ordinal = 0,name = "标识id")
    private String id;

    /**
     * 请求的借口方法路径
     */
    @JSONField(ordinal = 1,name = "请求的接口路径")
    private String urI;

    /**
     * 请求方法，get/post……
     */
    @JSONField(ordinal = 2,name = "请求方式")
    private String method;

    /**
     * 请求参数
     */
    @JSONField(ordinal = 3,name = "请求的参数")
    private Object[] args;

    /**
     * 定位
     */
    @JSONField(ordinal = 4,name = "定位")
    private String path;

}
