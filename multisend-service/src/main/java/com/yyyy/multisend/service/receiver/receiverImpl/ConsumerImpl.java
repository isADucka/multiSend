package com.yyyy.multisend.service.receiver.receiverImpl;

import com.yyyy.multisend.common.povo.po.logPo.Business;
import com.yyyy.multisend.common.povo.po.logPo.BusinessResult;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.dao.utils.LogUtils;
import com.yyyy.multisend.handler.pool.PoolHolder;
import com.yyyy.multisend.service.pojo.po.PoolMsg;
import com.yyyy.multisend.service.receiver.Consumer;
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
    private LogUtils logUtils;

    @Autowired
    private PoolMsg poolMsg;

    @Autowired
    private PoolHolder poolHolder;

    private final String businessName="cosumer_consumerMsg";

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
        //打点日志
        logUtils.print(Business.builder()
                .args(msgTask)
                .businessName(businessName)
                .result(BusinessResult.SUCCESS_SEDN_MQ)
                .businessId(msgTask.getBusinessId())
                .receiverType(msgTask.getReceiverType())
                .build());
        poolHolder.getPoolByName(poolName).execute(poolMsg);


    }


}
