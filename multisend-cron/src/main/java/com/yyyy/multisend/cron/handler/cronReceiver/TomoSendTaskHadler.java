package com.yyyy.multisend.cron.handler.cronReceiver;

import cn.hutool.core.util.StrUtil;
import com.dtp.core.thread.DtpExecutor;
import com.google.common.base.Throwables;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yyyy.multisend.cron.config.CronDynamicaPoolConfig;
import com.yyyy.multisend.cron.pool.ThreadPoolUtils;
import com.yyyy.multisend.handler.dutyHandler.dudyHandlerImpl.SendMqDuty;
import com.yyyy.multisend.handler.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author isADuckA
 * @Date 2023/5/29 0:26
 * 处理隔日九点发送消息
 */
@Service
@Slf4j
public class TomoSendTaskHadler {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    private DtpExecutor dtpExecutor = CronDynamicaPoolConfig.getXxlCronExecutor();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SendMqDuty sendMqDuty;

    //key
    private static final String TOMORROW_SEND = "night_shield_send";

    /**
     * 处理后台的 austin 定时任务消息
     */
    @XxlJob("tomoSendJob")
    public void execute() {
        log.info("TomoSendTaskHadler&&excete()"+"执行夜间屏蔽后隔天九点发送数据");
        System.out.println("隔天发送数据");
        threadPoolUtils.register(dtpExecutor);
        dtpExecutor.execute(() ->{
            //看看是否有数据
            while(redisUtils.lLen(TOMORROW_SEND)>0){
                String msgTask = redisUtils.lPop(TOMORROW_SEND);
                if (StrUtil.isNotBlank(msgTask)) {
                    try {
                        sendMqDuty.sendShileToMQ(msgTask);
                    } catch (Exception e) {
                        log.error("nightShieldLazyJob send  fail! e:{},params:{}", Throwables.getStackTraceAsString(e), msgTask);
                    }
                }
            }

        });
    }
}
