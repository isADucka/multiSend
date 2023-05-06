package com.yyyy.multisend.web.controller;

import com.yyyy.multisend.common.povo.po.MessageParm;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.service.receiver.rabbitmq.RabbitMqReceiver;
import com.yyyy.multisend.service.service.SendService;
import com.yyyy.multisend.web.annocation.MultisendAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author isADuckA
 * @Date 2023/4/9 18:57
 * 发送的controller
 */
@RestController
@RequestMapping("/sends")
@MultisendAspect
public class SendController {


    @Autowired
    private  SendService sendService;

    @Autowired
    private RabbitMqReceiver rabbitMqReceiver;

//    /***
//     * 发送消息
//     * @param messageModel
//     * @return
//     */
//    @GetMapping
//    public Result sendMsg(@RequestBody MessageModel messageModel){
//
//        return sendService.send(messageModel);
//    }

    /**
     * 发送消息
     * @param messageParm
     * @return
     */
    @GetMapping
    public Result sendMsg(@RequestBody MessageParm messageParm){
        System.err.println(messageParm);
        return  sendService.send(messageParm);
    }


}
