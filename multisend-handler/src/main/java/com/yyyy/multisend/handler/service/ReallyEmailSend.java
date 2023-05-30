package com.yyyy.multisend.handler.service;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.mail.MailUtil;
import com.yyyy.multisend.common.models.EmailModel;
import com.yyyy.multisend.common.models.Models;
import com.yyyy.multisend.common.models.SsmModle;
import com.yyyy.multisend.common.povo.po.logPo.Business;
import com.yyyy.multisend.common.povo.po.logPo.BusinessResult;
import com.yyyy.multisend.common.povo.vo.MsgName;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/19 14:44
 * 真正发送邮件的地方
 */
@Service
@Slf4j
public class ReallyEmailSend {

    @Autowired
    private LogUtils logUtils;

    private String businessName="ReallSend";

    public void realSend(MsgTask msgTask){

//        EmailModel models = (EmailModel) msgTask.getModels();

        Map<String, String> parm ;
        for(String receiver:msgTask.getReceiverAndParm().keySet()){
            parm=msgTask.getReceiverAndParm().get(receiver);
            if(!parm.containsKey(MsgName.URL)){
//                for(String receiver:msgTask.getReceiver()){
//                    System.err.println(receiver+"发送邮件");
//                    System.out.println(receiver);
//                    String send = MailUtil.send(receiver, parm.get(MsgName.TITLE), parm.get(MsgName.MSGCONTENT), false);
//                    System.out.println("发送结果"+send);
//                }
                System.out.println("发送多少次");
                String send = MailUtil.send(receiver, parm.get(MsgName.TITLE), parm.get(MsgName.MSGCONTENT), false);

            }else{
                //还没想好
//                for(String receiver:msgTask.getReceiver()){
//                    System.err.println(receiver+"发送邮件");
//                    System.out.println(receiver);
//                    String s = parm.get(MsgName.URL);
//                    System.out.println("我是邮件附件名称"+s);
//                    File file=new File(parm.get(MsgName.URL));
//                    if(!file.exists()){
//                        try {
//                            file.createNewFile();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
                try {
                    File file=new File(parm.get(MsgName.URL));
//                if(!file.exists()){

                        if(file.exists()) {

                             file.createNewFile();

                            logUtils.print(Business.builder()
                                    .args(msgTask)
                                    .businessName(businessName)
                                    .result(BusinessResult.SUCCESS)
                                    .receiverType(msgTask.getReceiverType())
                                    .businessId(msgTask.getBusinessId())
                                    .build());
                            String send = MailUtil.send(receiver, parm.get(MsgName.TITLE), parm.get(MsgName.MSGCONTENT), false, file);
                            log.info("真正成功发送数据给用户");

                            } else {
                                log.error("发送数据失败，该路径不存在");
                            }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            }

        }




}
