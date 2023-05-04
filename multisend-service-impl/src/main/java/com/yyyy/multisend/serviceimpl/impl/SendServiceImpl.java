package com.yyyy.multisend.serviceimpl.impl;

import com.alibaba.fastjson.JSON;
import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.common.povo.po.MessageParm;
import com.yyyy.multisend.common.povo.vo.Result;

import com.yyyy.multisend.common.ssm.SsmOfAliyun;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.Mapper.MessageModelDao;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.SendInTurn;
import com.yyyy.multisend.handler.service.ReallySsmSend;
import com.yyyy.multisend.service.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author isADuckA
 * @Date 2023/4/9 19:40
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private SsmOfAliyun ssmOfAliyun;

    @Autowired
    private SendInTurn sendInTurn;

    @Autowired
    private ReallySsmSend reallySsmSend;

    @Autowired
    private MessageModelDao messageModelDao;


    /**
     * 短信发送
     * @param messageParm 包含用户传进来的三个参数，模板id,接收者，参数
     * @return
     */
    @Override
    public Result send(MessageParm messageParm) {
        //处理参数
        //这个是包含一些占位符的参数
        Map parm = JSON.parseObject(messageParm.getContent(), Map.class);
        //处理发送对象为一个list
        Set<String> receiver=new HashSet<>();
        String[] split = messageParm.getReceiver().split(",");
        for(String receive:split){
            receiver.add(receive);
        }

//        Map parm = JSON.parseObject(messageParm.getContent(), Map.class);
        MsgTask msgTask=MsgTask.builder()
                .modelId(messageParm.getModelId())
                .receiver(receiver)
                .parm(parm)
                .build();

        //设置一条责任链
        DutyChain dutyChain=DutyChain.builder()
                .sendType(SendType.SEND)
                .msgTask(msgTask)
                .response(ResponseCodeType.DUTY_START)
                .build();

        //这一步看能不能再进行处理，不要显式调用
//        sendInTurn.before();
        //责任链处理后，开始真正发送消息
        DutyChain process = sendInTurn.process(dutyChain);
        if(process.getResponse().equals(ResponseCodeType.DUTY_SUCCESS)){

            //责任链走完就完了，只要完成将消息发送到消息队列，就完成了这个接口
            return Result.SUCCESS("发送成功");
        }

        return new Result(ResponseCodeType.ERROR_500,"请求失败");

    }
}
