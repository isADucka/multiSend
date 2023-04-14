package com.yyyy.multisend.web.controller;

import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.service.pojo.po.MessageModel;
import com.yyyy.multisend.service.receiver.rabbitmq.RabbitMqComsumer;
import com.yyyy.multisend.service.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author isADuckA
 * @Date 2023/4/9 18:57
 */
@RestController
@RequestMapping("/sends")
public class SendController {


    @Autowired
    private  SendService sendService;

    @Autowired
    private RabbitMqComsumer rabbitMqComsumer;

    /***
     * 发送消息
     * @param messageModel
     * @return
     */
    @GetMapping
    public Result sendMsg(@RequestBody MessageModel messageModel){
        System.out.println(messageModel.getReceiverIdType());
        return sendService.send(messageModel);
    }


}
