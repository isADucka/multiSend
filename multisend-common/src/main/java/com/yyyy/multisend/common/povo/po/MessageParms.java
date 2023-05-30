package com.yyyy.multisend.common.povo.po;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/5/10 15:43
 * 批量发送接口
 */
@Data
@Setter
@Getter
@Builder
public class MessageParms {

    /**
     * 批量发送的模板id
     */
    private Long modelId;

    /***
     * 批量发送的接受者和其他参数，
     */
    private List<Map> bathParm;

}
