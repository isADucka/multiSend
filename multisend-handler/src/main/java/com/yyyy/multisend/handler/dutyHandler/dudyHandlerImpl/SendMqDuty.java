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
     * 如果是多个对象，也是发一次请求，不会实行拆分
     * @param dutyChain
     * @return
     */
    @Override
    public DutyChain porcess(DutyChain dutyChain) {
        //拼接routingKey,由两个部分组成，由发送的类型加上信息，如短信+验证码

        //这个exchange得是固定的，routingKey是拼接的
        MsgTask msgTask = dutyChain.getMsgTask();
        String msg = JSONObject.toJSONString(msgTask);
        //routingkey也得随着改变，这里可以考虑让他变成receiverType的code值，或者添加一个desc的属性看起来更加清晰
        //已改
        String routingkey= String.valueOf(dutyChain.getMsgTask().getReceiverType());
        //这个地方记得将这个routingkey的值和队列进行绑定
        rabbitTemplate.convertAndSend(exchange,routingkey,msg);

        return  dutyChain;
    }
}
