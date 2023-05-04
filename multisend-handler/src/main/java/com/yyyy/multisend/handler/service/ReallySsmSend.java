package com.yyyy.multisend.handler.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.pojo.SsmClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/11 21:08
 */
@Service
public class ReallySsmSend {


    @Autowired
    private SsmClient ssmClient;


    /**
     * 真正发送短信的地方
     * @param msgTask
     * @return
     */
    public void realSend(MsgTask msgTask){

        try {

            //获取身份认证
            Client client = ssmClient.beforeSend();
            //获取发送请求
            List<SendSmsRequest> sendSmsRequests = ssmClient.sendRequest(msgTask);
            RuntimeOptions runtimeOptions = new RuntimeOptions();
            for(SendSmsRequest send:sendSmsRequests){
                SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(send, runtimeOptions);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 待定,看如何处理返回true,如何处理返回false
         */
//        return  true;
    }
}
