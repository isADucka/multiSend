package com.yyyy.multisend.service.service;

import com.yyyy.multisend.common.povo.vo.Result;

/**
 * @author isADuckA
 * @Date 2023/5/7 1:57
 * 链路追踪
 */
public interface TraceService {

    Result getTraceByReceiver(String receiver);
}
