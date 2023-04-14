package com.yyyy.multisend.serviceimpl.impl;

import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.common.povo.vo.Result;

import com.yyyy.multisend.common.ssm.SsmOfAliyun;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.SendInTurn;
import com.yyyy.multisend.handler.service.ReallySend;
import com.yyyy.multisend.service.pojo.po.MessageModel;
import com.yyyy.multisend.service.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ReallySend reallySend;


    /**
     * 短信发送
     * @param messageModel
     * @return
     */
    @Override
    public Result send(MessageModel messageModel) {
        //首先拆解参数封装
        MsgTask msgTask=MsgTask.builder()
                .receiever(messageModel.getReceiver())
                .context(messageModel.getMsgContext())
                .singleName(ssmOfAliyun.getSingleName())
                .templateId(ssmOfAliyun.getTemplateId())
                .receiverType(messageModel.getReceiverIdType())
                .msgType(messageModel.getMsgType())
                .build();
        System.out.println(messageModel.getReceiverIdType());
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
