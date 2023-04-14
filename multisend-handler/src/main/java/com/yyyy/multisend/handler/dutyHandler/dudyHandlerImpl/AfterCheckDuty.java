package com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl;


import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.handler.DutyChain;
import com.yyyy.multisend.handler.dutyHandler.Duty;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author isADuckA
 * @Date 2023/4/10 20:14
 * 处理MsgTask里面的值，比如说判断短信的签名和模板问题
 * 还有发送的对象和内容的值是否符合规范
 *
 */
@Service
public class AfterCheckDuty implements Duty {




    @Override
    public DutyChain porcess(DutyChain dutyChain) {
        dutyChain.setOver(true);
        MsgTask msgTask = dutyChain.getMsgTask();
        if(msgTask.getSingleName()==null||msgTask.getTemplateId()==null){
            dutyChain.setResponse(ResponseCodeType.DUTY_ERROR_MSGTASKNULL);
            return dutyChain;
        }
        //判断singleName和templateId能不能对的上,先放着
        if(false){
            dutyChain.setResponse(ResponseCodeType.DUTY_ERROR_MSGTASKNULL);
            return dutyChain;
        }

       //判断发送对象的规范
       if(!judge(msgTask.getReceiverType().getRuler(),msgTask.getReceiever())){
           dutyChain.setResponse(ResponseCodeType.DUTY_ERROR_MSGTASKNULL);
           return dutyChain;
       }

       //判断输入的内容是否符合规范
        //没想好，先放着
        if(msgTask.getMsgType().equals(MsgType.VERIFY)){

            msgTask.setContext("{\"code\":\""+msgTask.getContext()+"\"}");
        }


        //结束
        dutyChain.setOver(false);
        dutyChain.setResponse(ResponseCodeType.DUTY_SUCCESS);
        return  dutyChain;

    }


    /**
     * 利用正则表达式判断输入对象是否规范
     * @param ruler 真则表达式
     * @param judgeNumerber 被判断的对象
     * @return
     */
    public boolean judge(String ruler,String judgeNumerber){
        Matcher matcher = Pattern.compile(ruler).matcher(judgeNumerber);
        return matcher.matches();
    }




}
