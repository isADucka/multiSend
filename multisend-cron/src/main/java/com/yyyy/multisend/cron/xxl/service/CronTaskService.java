package com.yyyy.multisend.cron.xxl.service;

import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.cron.xxl.po.XxlJobInfo;

/**
 * @author isADuckA
 * @Date 2023/5/8 8:33
 * 定时模块相关逻辑
 */
public interface CronTaskService {


    /**
     * 获取执行器主键id
     * @param appName
     * @param jobHnadlerName
     * @return
     */
    Integer getGroupId(String appName,String jobHnadlerName);

    /**
     * 登录xxl-job后获取相对应的cookie
     * @return
     */
    String  getCookie();


    /**
     * 保存模板的内容，有id则更新，没有则插入记录
     * @param xxlJobInfo
     * @return
     */
    Result saveCronTask(XxlJobInfo xxlJobInfo);

    /**
     * 启动定时模板
     * @param cronTaskId
     */
    Result startCronTask(Integer cronTaskId);

    Result stopCronTask(Integer cronTaskId);
}

