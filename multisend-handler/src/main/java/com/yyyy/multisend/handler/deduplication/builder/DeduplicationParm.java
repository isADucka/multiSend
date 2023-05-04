package com.yyyy.multisend.handler.deduplication.builder;

import com.alibaba.fastjson.JSON;
import com.yyyy.multisend.common.ssm.MsgTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/27 11:13
 * 构建去重参数的po类
 */
@Data
@Builder
@AllArgsConstructor
public class DeduplicationParm {

    /**
     * 最主要的参数
     */
    private MsgTask msgTask;


    /**
     * 记录消息的时间戳
     */
    private Long deduplicationTime;

    /**
     * 某条消息所达到的次数
     */
    private Integer limit;

    /**
     * 去重的类型
     */
   private Integer deduplicationType;

}
