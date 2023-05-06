package com.yyyy.multisend.service.pojo.po;

import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.deduplication.ExitDeduplicationService;
import com.yyyy.multisend.handler.service.ReallyEmailSend;
import com.yyyy.multisend.handler.service.ReallySsmSend;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/4/14 1:30
 * 线程执行模块,利用线程池执行发送功能
 */
@Service
@Setter
public class PoolMsg implements Runnable{


    @Autowired
    private ReallySsmSend reallySsmSend;

    @Autowired
    private ReallyEmailSend reallyEmailSend;

    @Autowired
    private ExitDeduplicationService exitDeduplicationService;


    private MsgTask msgTask;

    @Override
    public void run() {

        //先去重
        exitDeduplicationService.deduplicationPorcess(msgTask);

//        System.out.println("执行");
//        reallySsmSend.realSend(msgTask);
        if(msgTask.getReceiver().size()!=0){

            System.out.println(msgTask.getReceiver());
            reallyEmailSend.realSend(msgTask);
        }

    }

}
