package com.yyyy.multisend.service.service;

import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.service.pojo.po.MessageModel;

/**
 * @author isADuckA
 * @Date 2023/4/9 19:08
 */
//@Service
public interface SendService {

    Result send(MessageModel messageModel);
}
