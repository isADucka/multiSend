package com.yyyy.multisend.handler.dutyHandler;

import com.yyyy.multisend.dao.handler.DutyChain;

/**
 * @author isADuckA
 * @Date 2023/4/10 19:24
 */
public interface Duty {
    /**
     * 处理消息的参数
     * @param dutyChain
     */
    DutyChain porcess(DutyChain dutyChain);
}
