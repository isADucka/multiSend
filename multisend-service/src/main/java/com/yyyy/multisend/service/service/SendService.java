package com.yyyy.multisend.service.service;

import com.yyyy.multisend.common.povo.po.MessageParm;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:08
 */
public interface SendService {

//    Result send(MessageModel messageModel);
    Result send(MessageParm messageParm);
}
