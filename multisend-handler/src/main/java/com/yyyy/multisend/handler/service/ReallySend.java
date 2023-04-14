package com.yyyy.multisend.handler.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.pojo.SsmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/4/11 21:08
 */
@Service
public class ReallySend {


    @Autowired
    private SsmClient ssmClient;


    /**
     * 真正发送短信的地方
     * @param msgTask
     * @return
     */
    public boolean realSend(MsgTask msgTask){

        try {

            //获取身份认证
            Client client = ssmClient.beforeSend();
            System.out.println("身份验证"+client);
            //获取发送请求
            SendSmsRequest sendSmsRequest = ssmClient.sendRequest(msgTask);
            RuntimeOptions runtimeOptions = new RuntimeOptions();

            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtimeOptions);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 待定
         */
        return  true;
    }
}
