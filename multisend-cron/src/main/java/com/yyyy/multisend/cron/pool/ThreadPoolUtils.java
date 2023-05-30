package com.yyyy.multisend.cron.pool;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author isADuckA
 * @Date 2023/5/9 0:56
 * 线程池，涉及到优雅关闭
 */
@Component
public class ThreadPoolUtils {
    @Autowired
    private ThreadCloseGRacefully threadCloseGRacefully;

    private static final String SOURCE_NAME = "multisend";


    /**
     * 1. 将当前线程池 加入到 动态线程池内
     * 2. 注册 线程池 被Spring管理，优雅关闭
     */
    public void register(DtpExecutor dtpExecutor) {
        //me:这个是到DtpRegistry中注册叫xx 执行器
        DtpRegistry.registerDtp(dtpExecutor,SOURCE_NAME);
        threadCloseGRacefully.registryExecutor(dtpExecutor);

    }
}
