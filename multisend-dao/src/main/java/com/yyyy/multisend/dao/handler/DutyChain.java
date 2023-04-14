package com.yyyy.multisend.dao.handler;

import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.common.ssm.MsgTask;
import lombok.*;

/**
 * @author isADuckA
 * @Date 2023/4/10 17:01
 * 责任链，主要是标记消息发送是否能够成功
 */
@Getter
@AllArgsConstructor
@Setter
@Builder
public class DutyChain{


    /**
     * 这个代表的是消息的类型，比如说发送，还是撤销
     */
    private SendType sendType;

    /**
     * 把发送消息必要的东西丢进去
     */
    private MsgTask msgTask;

    /**
     * 责任链处理结果
     */
    private ResponseCodeType response;

    /**
     * 是否中断，方便做判断
     */
    private boolean isOver;


}
