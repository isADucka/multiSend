package com.yyyy.multisend.cron.handler.po.vo;

/**
 * @author isADuckA
 * @Date 2023/5/10 2:03
 * 做延迟所需要的信息
 */
public class PendingConstant {
    /**
     * 阻塞队列大小
     */
    public static final Integer QUEUE_SIZE = 100;

    /**
     * 触发执行的数量阈值
     */
    public static final Integer NUM_THRESHOLD = 100;

    /**
     * batch 触发执行的时间阈值，单位毫秒【必填】
     */
    public static final Long TIME_THRESHOLD = 1000L;

    public static final Integer BATCH_RECEIVER_SIZE = 100;

}
