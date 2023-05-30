package com.yyyy.multisend.cron.handler.cronReceiver;


import com.dtp.core.thread.DtpExecutor;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yyyy.multisend.cron.config.CronDynamicaPoolConfig;
import com.yyyy.multisend.cron.pool.ThreadPoolUtils;
import com.yyyy.multisend.cron.service.RealCronTaskService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/5/8 22:31
 */
@Service
@Slf4j
public class CrontaskHandler {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    @Autowired
    private RealCronTaskService realCronTaskService;
    private DtpExecutor dtpExecutor = CronDynamicaPoolConfig.getXxlCronExecutor();

    /**
     * 处理后台的 austin 定时任务消息
     */
    @XxlJob("multisendJob")
    public void execute() {
        log.info("CronTaskHandler#execute messageTemplateId:{} cron exec!", XxlJobHelper.getJobParam());
        threadPoolUtils.register(dtpExecutor);
        Long messageTemplateId = Long.valueOf(XxlJobHelper.getJobParam());
        dtpExecutor.execute(() -> realCronTaskService.cronHandler(messageTemplateId));

    }
}
