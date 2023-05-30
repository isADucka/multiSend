package com.yyyy.multisend.cron.handler.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author isADuckA
 * @Date 2023/5/9 20:47
 * 实现延迟和批量加载的一些参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PendingParm<T> {
     /**
     * 阻塞队列实现类
     */
    private BlockingQueue<T> queue;

    /**
     *  批量加载的量
     */
    private Integer numThreshold;

    /**
     * batch 触发执行的时间阈值，单位毫秒
     */
    private Long timeThreshold;

    /**
     * 消费线程池实例
     */
    protected ExecutorService executorService;
}
