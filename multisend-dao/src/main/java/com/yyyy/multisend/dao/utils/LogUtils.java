package com.yyyy.multisend.dao.utils;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.CustomLogListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyyy.multisend.common.povo.po.logPo.Business;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author isADuckA
 * @Date 2023/5/5 16:58
 * 打印日志
 */
@Slf4j
@Component
public class LogUtils extends CustomLogListener {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createLog(LogDTO logDTO) throws Exception {
        //这个暂定
        log.info(JSON.toJSONString(logDTO));
    }


    /**
     * 业务执行过程打印记录
     * @param business
     */
    public void print(Business business){
        business.setTime(System.currentTimeMillis());
        String message= JSON.toJSONString(business);
        //这个地方得发送消息到mq
        String msg = JSONObject.toJSONString(business);
        System.err.println(String.valueOf(business.getReceiverType()));
        rabbitTemplate.convertAndSend("log",String.valueOf(business.getReceiverType()),msg);
        log.info(message);
    }


}
