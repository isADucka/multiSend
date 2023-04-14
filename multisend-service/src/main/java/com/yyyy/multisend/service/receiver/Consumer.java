package com.yyyy.multisend.service.receiver;

import com.yyyy.multisend.common.ssm.MsgTask;

/**
 * @author isADuckA
 * @Date 2023/4/13 10:22
 */
public interface Consumer {

    void comsumerMsg(MsgTask msgTask);
}
