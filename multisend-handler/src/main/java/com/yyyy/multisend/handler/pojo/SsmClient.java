package com.yyyy.multisend.handler.pojo;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.common.ssm.SsmOfAliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/4/11 21:11
 * 做好发送短信前的准备工作
 */
@Service
public class SsmClient {
//
    @Autowired
    private SsmOfAliyun ssmOfAliyun;


    public Client beforeSend(){

////        SsmConstant ssmConstant = new SsmConstant();
//        ssmOfAliyun=new SsmOfAliyun();
//        System.out.println(ssmOfAliyun.getAccessKeyId());

        try {

//            System.out.println("不要null了，球球"+ssmOfAliyun);
            Config config = new Config();
            config.setAccessKeyId(ssmOfAliyun.getAccessKeyId());
            config.setAccessKeySecret(ssmOfAliyun.getAccessKeySecret());
            config.setEndpoint(ssmOfAliyun.getEndpoint());
            return new Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 短信发送请求
     * @param msgTask
     */
    public SendSmsRequest sendRequest(MsgTask msgTask){
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(msgTask.getSingleName())
                .setTemplateCode(msgTask.getTemplateId())
                .setPhoneNumbers(msgTask.getReceiever())
                .setTemplateParam(msgTask.getContext());
        System.out.println(sendSmsRequest.toString());

        return sendSmsRequest;

    }
}
