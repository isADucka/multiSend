package com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl;

import com.alibaba.fastjson.JSONObject;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**
 * @author isADuckA
 * @Date 2023/4/12 20:59
 * 发送消息到mq
 */

@Service
public class SendMqDuty implements Duty {
    @Value("${multisend.rabbitmq.exchange}")
    private String exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 真正发送消息到mq的地方
     * @param dutyChain
     * @return
     */
    @Override
    public DutyChain porcess(DutyChain dutyChain) {
        //拼接routingKey,由两个部分组成，由发送的类型加上信息，如短信+验证码

        //这个exchange得是固定的，routingKey是拼接的
        MsgTask msgTask = dutyChain.getMsgTask();
        String s = JSONObject.toJSONString(msgTask);
        rabbitTemplate.convertAndSend(exchange,"ssm",s);

        return  dutyChain;
    }
}
