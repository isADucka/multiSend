package com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * @author isADuckA
 * @Date 2023/4/12 20:59
 * 发送消息到mq
 */

@Service
@Slf4j
public class SendMqDuty implements Duty {
    @Value("${multisend.rabbitmq.exchange}")
    private String exchange;
    private String routingkey;

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
        try {
        //拼接routingKey,由两个部分组成，由发送的类型加上信息，如短信+验证码

        //这个exchange得是固定的，routingKey是拼接的
        MsgTask msgTask = dutyChain.getMsgTask();
//        System.out.println("niem++++"+msgTask.getReceiverAndParm());
////        String msg = JSONObject.toJSONString(msgTask);
        String msg;

            msg= new ObjectMapper().writeValueAsString(msgTask);

        //routingkey也得随着改变，这里可以考虑让他变成receiverType+msgType的code值，或者添加一个desc的属性看起来更加清晰
        //已改
        routingkey= String.valueOf(dutyChain.getMsgTask().getReceiverType()+""+dutyChain.getMsgTask().getMsgType());
        //这个地方记得将这个routingkey的值和队列进行绑定
//        System.err.println("msg________"+msg)

         if(!(exchange==null && routingkey==null)){
             System.out.println();
             rabbitTemplate.convertAndSend(exchange,routingkey,msg);
             System.out.println("routingkey+======="+routingkey);
             dutyChain.setOver(false);
             return dutyChain;
         }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.error("消息队列的exchange或者是routingkey有误");
        return  dutyChain;
    }


    /**
     * 重新发送夜间屏蔽的消息到mq
     * @param msg
     */
    public void sendShileToMQ(String msg)  {
        if(msg==null){
            return;
        }
        try {

//        //转化为msg为符合的参数
//            System.out.println("msg的值为——————————"+msg);
////            ObjectMapper mapper = new ObjectMapper();
////

            //先将原始的数据转化为对象
            MsgTask msgTask = JSONObject.parseObject(msg, MsgTask.class);
            System.out.println("我是msgTask——————————————"+msgTask);
            routingkey=msgTask.getReceiverType()+""+msgTask.getMsgType();

            //克隆一份更改下发送的数据，避免死循环一直进入发送不出去
//            MsgTask lastMsg = ObjectUtil.clone(msgTask).setShield(0);
            MsgTask lastMsg = msgTask.setShield(0);
            System.out.println("我是lastMsg——————————————"+lastMsg);
            //转化为json
            msg=new ObjectMapper().writeValueAsString(lastMsg);

            System.out.println("隔天发送的数据routingkey的值为："+routingkey);
            rabbitTemplate.convertAndSend(exchange,routingkey,msg);

            log.info("夜间屏蔽隔天发送的数据已重新发送到mq");
        } catch (JsonProcessingException e) {
            log.error("夜间屏蔽隔天发送的数据发送mq失败，所在位置为："+"com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.SendMqDuty&&sendShileToMQ");
            e.printStackTrace();
        }





    }
}
