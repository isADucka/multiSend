package com.yyyy.multisend.cron.config;

import cn.hutool.core.thread.ExecutorBuilder;

import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.support.ThreadPoolBuilder;
import com.dtp.core.thread.DtpExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author isADuckA
 * @Date 2023/5/9 8:51
 * 动态线程池配置
 */
public class CronDynamicaPoolConfig {
    /**
     * 接收到xxl-job请求的线程池名
     */
    public static final String EXECUTE_XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";


    /**
     * 业务：消费pending队列实际的线程池
     * 配置：核心线程可以被回收，当线程池无被引用且无核心线程数，应当被回收
     * 动态线程池且被Spring管理：false
     *
     * @return
     */
    public static ExecutorService getConsumePendingThreadPool() {
        return ExecutorBuilder.create()
                .setCorePoolSize(DynamicPoolConstant.COMMON_CORE_POOL_SIZE)
                .setMaxPoolSize(DynamicPoolConstant.COMMON_MAX_POOL_SIZE)
                .setWorkQueue(DynamicPoolConstant.BIG_BLOCKING_QUEUE)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(DynamicPoolConstant.SMALL_KEEP_LIVE_TIME, TimeUnit.SECONDS)
                .build();
    }


    /**
     * 业务：接收到xxl-job请求的线程池
     * 配置：不丢弃消息，核心线程数不会随着keepAliveTime而减少(不会被回收)
     * 动态线程池且被Spring管理：true
     *
     * @return
     */
    public static DtpExecutor getXxlCronExecutor() {
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(EXECUTE_XXL_THREAD_POOL_NAME)
                .corePoolSize(DynamicPoolConstant.COMMON_CORE_POOL_SIZE)
                .maximumPoolSize(DynamicPoolConstant.COMMON_MAX_POOL_SIZE)
                .keepAliveTime(DynamicPoolConstant.COMMON_KEEP_LIVE_TIME)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName())
                .allowCoreThreadTimeOut(false)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(), DynamicPoolConstant.COMMON_QUEUE_SIZE, false)
                .buildDynamic();
    }
}
