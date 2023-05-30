package com.yyyy.multisend.service.service;

import com.yyyy.multisend.common.povo.po.MessageParm;
import com.yyyy.multisend.common.povo.po.MessageParms;
import com.yyyy.multisend.common.povo.vo.Result;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:08
 */
public interface SendService {


    /**
     * 发送消息，目前为处理短信和邮件
     * @param messageParm
     * @return
     */
    Result send(MessageParm messageParm);


    Result sendBatch(MessageParms messageParms);


}
