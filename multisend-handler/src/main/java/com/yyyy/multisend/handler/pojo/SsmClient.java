package com.yyyy.multisend.handler.pojo;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.yyyy.multisend.common.models.SsmModle;
import com.yyyy.multisend.common.povo.vo.MsgName;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.common.ssm.SsmOfAliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
    public List<SendSmsRequest> sendRequest(MsgTask msgTask){
        //根据短信的类型msgType来决定短信签名和短信模板id
        //这里先暂定，可能会再写一个枚举类，（MsgType.getcode(),singleName,templateId）的形式
        //先默认是验证码形式

        List<SendSmsRequest> requestList=new LinkedList<>();

        for(String user:msgTask.getReceiverAndParm().keySet()) {
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(ssmOfAliyun.getSingleName())
                    .setTemplateCode(ssmOfAliyun.getTemplateId())
//                .setTemplateParam(msgTask.getParm().get(MsgName.MSGCONTENT));
                    .setTemplateParam(msgTask.getReceiverAndParm().get(user).get(MsgName.MSGCONTENT))
                    .setPhoneNumbers(user);
//          .setPhoneNumbers(msgTask.getReceiver())
//            for (String receiver : msgTask.getReceiver()) {
//                requestList.add(sendSmsRequest.setPhoneNumbers(receiver));
                requestList.add(sendSmsRequest);
//            }
        }
        return requestList;

    }
}
