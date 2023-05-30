package com.yyyy.multisend.web.service;

import cn.hutool.core.util.ObjectUtil;
import com.yyyy.multisend.common.enums.ResponseCodeType;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.cron.xxl.po.XxlJobInfo;
import com.yyyy.multisend.cron.xxl.service.CronTaskService;
import com.yyyy.multisend.cron.xxl.utils.XxlJobUtils;
import com.yyyy.multisend.dao.Mapper.MessageModelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author isADuckA
 * @Date 2023/5/12 1:15
 */
@Service
@Slf4j
public class CronSendServiceImpl  implements CronSendService{
    @Autowired
    private MessageModelDao messageModelDao;
    @Autowired
    private XxlJobUtils xxlJobUtils;
    @Autowired
    private CronTaskService cronTaskService;

    /**
     * 处理定时任务
     * @param id
     * @return
     */
    @Override
    public Result startCrontask(Long id) {
        System.out.println(id);
        //查询这个模板
        MessageModel messageModel = messageModelDao.findById(id).get();
        if(messageModel==null){
            log.error("com.yyyy.multisend.web.service.CronSendServiceImpl&startCrontask"+":获取定时模板失败");
            return new Result("获取定时模板失败");
        }
        //获取定时模块的id,如果是没有的话这里会返回null;
        Integer taskId = messageModel.getCronTaskId();
        //查询这个模板的taskId存不存在
        XxlJobInfo xxlJobInfo = xxlJobUtils.buildXxlJobInfo(messageModel);

        Result<Integer> result = cronTaskService.saveCronTask(xxlJobInfo);
        if(result.getCode()== ResponseCodeType.OK_200 && result.getMsg()!=null){
            //这一步当插入的时候，因为这个时候会返回一个定时模块id
            taskId=result.getMsg();
        }
        System.out.println("task的值为："+taskId);
        //拿到定时模块的id后，就需要真正启动定时任务
        if(taskId!=null){
            cronTaskService.startCronTask(taskId);
            //将模板改为启动状态,更新定时任务的id
            MessageModel remessageModel = ObjectUtil.clone(messageModel).setCronStatus(1).setCronTaskId(taskId);
            messageModelDao.save(remessageModel);
            return Result.SUCCESS("启动成功");
        }

        return new Result("启动失败");
    }

    @Override
    public Result stopCronTask(Long id) {
        //
        MessageModel messageModel = messageModelDao.findById(id).get();
        if(messageModel==null){
            return new Result("该模板不存在");
        }
        messageModel.setCronStatus(0);
        messageModelDao.save(messageModel);
        return cronTaskService.stopCronTask(messageModel.getCronTaskId());

    }
}
