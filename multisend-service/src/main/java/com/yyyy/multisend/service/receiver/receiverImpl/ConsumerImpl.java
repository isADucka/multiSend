package com.yyyy.multisend.service.receiver.receiverImpl;

import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.pool.PoolHolder;
import com.yyyy.multisend.handler.pool.ThreadConfig;
import com.yyyy.multisend.handler.service.ReallySsmSend;
import com.yyyy.multisend.service.pojo.po.PoolMsg;
import com.yyyy.multisend.service.receiver.Consumer;
import com.yyyy.multisend.service.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/4/13 10:38
 * 从消息队列中拉取消息
 */

@Service
public class ConsumerImpl implements Consumer {


    @Autowired
    private PoolMsg poolMsg;

    @Autowired
    private PoolHolder poolHolder;

    /**
     * 处理从消息队列中拿出来的消息
     * 现在这里是发送短信和邮件
     * @param msgTask
     */
    @Override
    public void comsumerMsg(MsgTask msgTask) {
        //调用线程池去分别执行
        //先拼接这个线程池的名称
        String poolName = msgTask.getReceiverType()+ "" + msgTask.getMsgType();
        poolMsg.setMsgTask(msgTask);
//        PoolHolder poolHolder=new PoolHolder();

        poolHolder.getPoolByName(poolName).execute(poolMsg);


    }


}
