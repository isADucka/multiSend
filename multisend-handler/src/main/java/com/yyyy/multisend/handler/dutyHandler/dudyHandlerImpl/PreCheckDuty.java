package com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl;

import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/4/10 19:27
 * 处理前置参数
 */
@Service
public class PreCheckDuty implements Duty {

    @Override
    public DutyChain porcess(DutyChain dutyChain) {
        //这个是用于判断责任链接是否有问题的，
        dutyChain.setOver(true);

        //处理dutyChin的外壳
        //先判断整体是不是空的
        if(dutyChain==null){
             dutyChain.setResponse(ResponseCodeType.DUTY_ERROR_NULL);
             return dutyChain;
        }
        //判断SendType，是不是发送或者是撤回
        if(dutyChain.getSendType()!= SendType.SEND&&dutyChain.getSendType()!=SendType.ROLLOBACK){
            //证明有问题
            dutyChain.setResponse(ResponseCodeType.DUTY_EEROR_SENDTYPENULL);
            return dutyChain;
        }

        //判断msgTask的值是不是为null
        if(dutyChain.getMsgTask()==null){
            dutyChain.setResponse(ResponseCodeType.DUTY_ERROR_MSGTASKNULL);
            return dutyChain;
        }
        //如果都判断完了，说明整体没问题
        dutyChain.setOver(false);
        return dutyChain;
    }
}
