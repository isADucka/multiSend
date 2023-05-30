package com.yyyy.multisend.cron.handler.penging;

import com.yyyy.multisend.common.povo.po.MessageParms;
import com.yyyy.multisend.cron.config.CronDynamicaPoolConfig;
import com.yyyy.multisend.cron.handler.po.PendingParm;
import com.yyyy.multisend.cron.handler.po.vo.PendingConstant;
import com.yyyy.multisend.service.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author isADuckA
 * @Date 2023/5/10 1:49
 * 继承自AchieveLazy的类
 * 做到延时处理人群，批量进行
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class AchieveLazyImpl extends AchieveLazy<MessageParms>{

    @Autowired
    private SendService sendService;

    public AchieveLazyImpl() {
        PendingParm<MessageParms> pendingParm = new PendingParm<>();
        pendingParm.setQueue(new LinkedBlockingQueue(PendingConstant.QUEUE_SIZE))
                .setTimeThreshold(PendingConstant.TIME_THRESHOLD)
                .setNumThreshold(PendingConstant.BATCH_RECEIVER_SIZE)
                .setExecutorService(CronDynamicaPoolConfig.getConsumePendingThreadPool());
        this.pendingParm=pendingParm;
    }


    @Override
    public void doHandle(List<MessageParms> list) {
        //做到成批发送
        System.out.println(list);
        //发到bath进行发送到mq
        if(list!=null){
            for(MessageParms messageParms:list){
                 sendService.sendBatch(messageParms);
            }
        }else{
            log.info("处理定时任务的参数逃跑了，position={}","AchieveLazyImpl#dohandle");
        }


    }
}
