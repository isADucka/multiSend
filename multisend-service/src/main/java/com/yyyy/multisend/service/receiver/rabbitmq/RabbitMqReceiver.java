package com.yyyy.multisend.service.receiver.rabbitmq;


import com.alibaba.fastjson.JSONObject;
import com.yyyy.multisend.common.models.Models;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.service.receiver.Consumer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.reflect.misc.ReflectUtil;

import java.lang.reflect.Field;

/**
 * @author isADuckA
 * @Date 2023/4/13 10:41
 * rabbitMq进行消费
 */
@Service
public class RabbitMqReceiver {

    @Value("${multisend.rabbitmq.queueName}")
    private String queueName;



    @Autowired
    private Consumer consumer;

    /**
     * 这里就完成了消费，把消息从消息队列里面拿出来
     * 应该是得把他放到线程池里面去发送消息，先不写线程池
     */
    @RabbitHandler
    @RabbitListener(queues = "${multisend.rabbitmq.queueName}")
    public void comsume(Message message) {

        byte[] body = message.getBody();
        MsgTask msgTask = JSONObject.parseObject(body, MsgTask.class);
//        Models models = msgTask.getModels();

//        System.out.println(msgTask.getContext());
        //调用消费的service来处理从队列中拿出来的消息
        //这里调用service是因为如果有多个中间件的话就会很多重复，所以调用一个统一的去执行

        consumer.comsumerMsg(msgTask);



    }
}

