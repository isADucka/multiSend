package com.yyyy.multisend.common.povo.po.logPo;

import lombok.Builder;
import lombok.Data;

/**
 * @author isADuckA
 * @Date 2023/5/5 17:10
 * 存储业务日志，比如说这个业务什么时候生成，执行什么业务
 */
@Data
@Builder
public class Business {
    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 业务名称
     */
    private  String businessName;

    /**
     * 业务执行的时间
     */
    private long time;

    /**
     * 执行业务的数据
     */
    private Object args;

    /**
     * 业务执行的结果
     */
    private BusinessResult result;

    private Integer receiverType;
}

