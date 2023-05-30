package com.yyyy.multisend.cron.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.util.StrUtil;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.povo.po.MessageParms;
import com.yyyy.multisend.cron.handler.penging.AchieveLazyImpl;
import com.yyyy.multisend.cron.po.CountFileRow;
import com.yyyy.multisend.cron.utils.CsvReadUtils;
import com.yyyy.multisend.dao.Mapper.MessageModelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/5/9 9:21
 */
@Service
@Slf4j
public class RealCronTaskServiceImpl implements  RealCronTaskService{
    @Autowired
    private MessageModelDao messageModelDao;
    @Autowired
    private ApplicationContext applicationContext;



    /**
     * 真正处理定时模块的方法
     * @param cronTaskId
     * 步骤：从数据库读数据—>读取目标人群文件—>对每一行做处理
     */
    @Override
    public void cronHandler(Long cronTaskId) {
        MessageModel messageModel = messageModelDao.findById(cronTaskId).get();
        if(messageModel.getTargetPath()==null){
            log.info("目标人群文件路径失效");
            return;
        }
        AchieveLazyImpl achieveLazy=applicationContext.getBean(AchieveLazyImpl.class);
        //这个是放置所有要执行批操作的参数
        List<Map> bathParm=new LinkedList<>();
        //读取目标文件
        Long rowCountOfCsv = CsvReadUtils.getRowCountOfCsv(messageModel.getTargetPath(), new CountFileRow());
        System.out.println("长度"+rowCountOfCsv);

        //读取每一行的元素
        CsvReadUtils.getCsvRow(messageModel.getTargetPath(), row -> {
            //判断该行是不是为空
            if(CollUtil.isEmpty(row.getFieldMap())|| StrUtil.isBlank(row.getFieldMap().get(CsvReadUtils.RECEIVER))){
                //这一行没有参数或者是缺少接收者,结束这一行的操作
                return;
            }
            // 3. 每一行处理交给LazyPending,这里先要处理下参数，原本读取的行数是包括接收者的，这里要把他剔除,应该不是剔除;
            bathParm.add(row.getFieldMap());


          MessageParms messageParm=MessageParms.builder()
                        .modelId(messageModel.getModelId())
                        .bathParm(bathParm)
                        .build();

           achieveLazy.pending(messageParm);

            // 4. 判断是否读取文件完成回收资源且更改状态
            onComplete(row, rowCountOfCsv, achieveLazy, cronTaskId);
            System.err.println("结束");

        });

    }
    //me;已读
    /**
     * 文件遍历结束时
     * 1. 暂停单线程池消费(最后会回收线程池资源)
     * 2. 更改消息模板的状态(暂未实现)
     *
     * @param row
     * @param countCsvRow
     * @param crowdBatchTaskPending
     * @param messageTemplateId
     */
    private void onComplete(CsvRow row, long countCsvRow, AchieveLazyImpl crowdBatchTaskPending, Long messageTemplateId) {
        System.out.println("111111111111"+row.getOriginalLineNumber() );
        //row.getOriginalLineNumber() 是获取当前的行数
        if (row.getOriginalLineNumber() == countCsvRow) {
            crowdBatchTaskPending.setStop(true);
            log.info("messageTemplate:[{}] read csv file complete!", messageTemplateId);
        }
    }
}
