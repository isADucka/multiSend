package com.yyyy.multisend.service.pojo.po;

import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.service.ReallySend;
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
    private ReallySend reallySend;

    private MsgTask msgTask;

    @Override
    public void run() {
        reallySend.realSend(msgTask);
    }

}
