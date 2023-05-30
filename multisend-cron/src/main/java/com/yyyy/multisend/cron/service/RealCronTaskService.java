package com.yyyy.multisend.cron.service;

/**
 * @author isADuckA
 * @Date 2023/5/9 9:20
 * 真正处理定时模块
 */
public interface RealCronTaskService {

    public void cronHandler(Long CronTaskId);
}
