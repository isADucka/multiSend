package com.yyyy.multisend.serviceimpl.impl;


import com.alibaba.fastjson.JSON;
import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.povo.po.MessageParm;
import com.yyyy.multisend.common.povo.po.MessageParms;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.Mapper.MessageModelDao;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.SendInTurn;
import com.yyyy.multisend.service.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author isADuckA
 * @Date 2023/4/9 19:40
 */
@Service
@Slf4j
public class SendServiceImpl implements SendService {

//    @Autowired
//    private SsmOfAliyun ssmOfAliyun;

    @Autowired
    private SendInTurn sendInTurn;

//    @Autowired
//    private ReallySsmSend reallySsmSend;
//
//    @Autowired
//    private MessageModelDao messageModelDao;

    @Autowired
    private MessageModelDao messageModelDao;

//    @Autowired
//    private XxlJobUtils xxlJobUtils;
//
//    @Autowired
//    private CronTaskService cronTaskService;


    /**
     *  包含用户传进来的三个参数，模板id,接收者，参数
     * messageParm
     * @param messageParm
     * @return
     */
    @Override
    public Result send(MessageParm messageParm) {
        //处理参数
        //这个是包含一些占位符的参数
        Map parm = JSON.parseObject(messageParm.getContent(), Map.class);
        //处理发送对象为一个list
//        Set<String> receiver=new HashSet<>();
        Map<String,Map<String,String>> receiverAndParm=new HashMap<>();
        String[] split = messageParm.getReceiver().split(",");
        for(String receive:split){
//            receiver.add(receive);
            receiverAndParm.put(receive,parm);
        }


//        Map parm = JSON.parseObject(messageParm.getContent(), Map.class);
        MsgTask msgTask=MsgTask.builder()
                .modelId(messageParm.getModelId())
                .receiverAndParm(receiverAndParm)
                .build();

        //设置一条责任链
        DutyChain dutyChain=DutyChain.builder()
                .sendType(SendType.SEND)
                .msgTask(msgTask)
                .response(ResponseCodeType.DUTY_START)
                .build();


        // 责任链处理后，开始真正发送消息
        DutyChain process = sendInTurn.process(dutyChain);
        if(process.getResponse().equals(ResponseCodeType.DUTY_SUCCESS)){

            //责任链走完就完了，只要完成将消息发送到消息队列，就完成了这个接口
            log.info("成功发送消息到消息队列");
            return Result.SUCCESS("发送成功");
        }
        log.info("位置{}"+"com.yyyy.multisend.serviceimpl.impl.SendServiceImpl.send"+"出错，发送到消息队列失败");
        return new Result(ResponseCodeType.ERROR_500,"请求失败");

    }

    /**
     * 成批发送参数
     * @param messageParms
     * @return
     */
    @Override
    public Result sendBatch(MessageParms messageParms) {
        //处理参数
        //这个是包含一些占位符的参数
//        Map parm = JSON.parseObject(messageParm.getContent(), Map.class);
        Map<String,Map<String,String>> receiverAndParm=new HashMap<>();
        Map<String,String> parms=new HashMap<>();
        for(Map parm:messageParms.getBathParm()){
            String receiver= (String) parm.get("receiver");
            parm.remove(receiver);
            receiverAndParm.put(receiver,parm);
        }


        MsgTask msgTask=MsgTask.builder()
                .modelId(messageParms.getModelId())
                .receiverAndParm(receiverAndParm)
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
