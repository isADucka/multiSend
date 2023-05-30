package com.yyyy.multisend.web.controller;

import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.service.service.TraceService;
import com.yyyy.multisend.web.annocation.MultisendAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author isADuckA
 * @Date 2023/5/7 1:53
 * 链路追踪
 */
@RestController
@RequestMapping("/trace")
@MultisendAspect
public class TraceController {

    @Autowired
    private TraceService traceService;

    /**
     * 根据接收者来获取查询消息下发结果
     * @param receiverId
     * @return
     */
    @PostMapping("/receiver")
    public Result traceByReceiver(String receiverId){
        return  traceService.getTraceByReceiver(receiverId);
    }
}
