package com.yyyy.multisend.handler.service;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.mail.MailUtil;
import com.yyyy.multisend.common.models.EmailModel;
import com.yyyy.multisend.common.models.Models;
import com.yyyy.multisend.common.models.SsmModle;
import com.yyyy.multisend.common.povo.vo.MsgName;
import com.yyyy.multisend.common.ssm.MsgTask;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/19 14:44
 * 真正发送邮件的地方
 */
@Service
public class ReallyEmailSend {

    public void realSend(MsgTask msgTask){

//        EmailModel models = (EmailModel) msgTask.getModels();

        Map<String, String> parm = msgTask.getParm();
        if(!parm.containsKey(MsgName.URL)){
            for(String receiver:msgTask.getReceiver()){
                System.err.println(receiver+"发送邮件");
                System.out.println(receiver);
                String send = MailUtil.send(receiver, parm.get(MsgName.TITLE), parm.get(MsgName.MSGCONTENT), false);
                System.out.println("发送结果"+send);
            }
        }else{
            //还没想好
        }


    }
}
