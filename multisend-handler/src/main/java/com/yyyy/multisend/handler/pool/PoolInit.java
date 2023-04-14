package com.yyyy.multisend.handler.pool;

import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.support.ThreadPoolBuilder;
import com.dtp.core.thread.DtpExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @author isADuckA
 * @Date 2023/4/13 19:04
 * 初始化线程池
 */
public class PoolInit {

    //直接cv
    public static DtpExecutor getDtpExecutor(String poolName){
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(poolName)
                .corePoolSize(1)
                .maximumPoolSize(1)
                .keepAliveTime(60)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                .allowCoreThreadTimeOut(false)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(),2, false)
                .buildDynamic();
    }
}
