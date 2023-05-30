package com.yyyy.multisend.cron.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author isADuckA
 * @Date 2023/5/9 0:59
 * 优雅关闭线程池
 */
@Component
@Slf4j
public class ThreadCloseGRacefully implements ApplicationListener<ContextClosedEvent> {
    private final List<ExecutorService> POOLS = Collections.synchronizedList(new ArrayList<>(12));

    /**
     * 等待关闭的最长时间
     */
    private final long WAIT_TIME=20;


    /**
     * 等待时间的单位
     */
    private final TimeUnit TIME_UNIT=TimeUnit.SECONDS;


    /**
     * 添加到池子里面去
     * @param executor
     */
    public void registryExecutor(ExecutorService executor) {
        POOLS.add(executor);
    }

    /**
     * 监听到关闭的时候，就会执行这个
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("容器即将关闭，还有{}线程需要处理",POOLS.size());
        if(POOLS.size()==0){
            //此时不需要做任何处理
            return;
        }
       for(ExecutorService pool:POOLS){
           //正在关闭线程，不会强迫关闭，只是任务不会再添加，会等待未执行的任务执行完
           pool.shutdown();
           try {
               if(pool.awaitTermination(WAIT_TIME,TIME_UNIT)){
                   log.info("超时关闭-------->>正在关闭线程{}",pool);
               }
           } catch (InterruptedException e) {
               log.info("超时关闭-------->>正在关闭线程{}",pool);
               Thread.currentThread().interrupt();
           }
       }
    }
}
