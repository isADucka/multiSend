package com.yyyy.multisend.cron.handler.penging;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Throwables;
import com.yyyy.multisend.cron.handler.po.PendingParm;
import com.yyyy.multisend.handler.pool.PoolInit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author isADuckA
 * @Date 2023/5/9 20:40
 * 实现延迟发送
 */
@Slf4j
@Data
public abstract class AchieveLazy<T> {

    /**
     * 子类构造方法必须初始化该参数
     */
    protected PendingParm<T> pendingParm;

    /**
     * 批量装载任务
     */
    private List tasks = new ArrayList<>();

    /**
     * 上次执行的时间
     */
    private Long lastHandleTime = System.currentTimeMillis();

    /**
     * 是否终止线程
     */
    private volatile Boolean stop = false;

    /**
     * 单线程消费 阻塞队列的数据
     */
    @PostConstruct
    public void initConsumePending() {
        //获取执行的 线程
        ExecutorService executorService = PoolInit.getDtpExecutor("cron task");
        executorService.execute(() -> {
            while (true) {
                try {
                    //从队列中取出要执行的任务，如果取不到则等待一段时间继续读取
                    T obj = pendingParm.getQueue().poll(pendingParm.getTimeThreshold(), TimeUnit.MILLISECONDS);
                    if (null != obj) {
                        tasks.add(obj);
                    }

                    // 判断是否停止当前线程
                    if (stop && CollUtil.isEmpty(tasks)) {
                        executorService.shutdown();
                        break;
                    }

                    // 处理条件：1. 数量超限 2. 时间超限
                    if (CollUtil.isNotEmpty(tasks) && dataReady()) {

                        List<T> taskRef = tasks;
                        //又重新赋值
                        tasks = Lists.newArrayList();
                        lastHandleTime = System.currentTimeMillis();

                        // 具体执行逻辑
                        pendingParm.getExecutorService().execute(() -> this.handle(taskRef));
                    }


                } catch (Exception e) {
                    log.error("Pending#initConsumePending failed:{}","获取定时任务失败");
                }
            }
        });

    }

    /**
     * 1. 数量超限
     * 2. 时间超限
     *
     * @return
     */
    private boolean dataReady() {
        return tasks.size() >= pendingParm.getNumThreshold() ||
                (System.currentTimeMillis() - lastHandleTime >= pendingParm.getTimeThreshold());
    }

    /**
     * 将元素放入阻塞队列中
     *
     * @param t
     */
    public void pending(T t) {
        try {
            pendingParm.getQueue().put(t);
        } catch (InterruptedException e) {
            log.error("Pending#pending error:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 消费阻塞队列元素时的方法
     *
     * @param t
     */
    public void handle(List<T> t) {
        if (t.isEmpty()) {
            return;
        }
        try {
            doHandle(t);
        } catch (Exception e) {
            log.error("Pending#handle failed:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 处理阻塞队列的元素 真正方法
     *
     * @param list
     */
    public abstract void doHandle(List<T> list);

}
