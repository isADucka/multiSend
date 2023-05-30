package com.yyyy.multisend.handler.shield;

import com.yyyy.multisend.common.ssm.MsgTask;
import org.springframework.stereotype.Service;

/**
 * @author isADuckA
 * @Date 2023/5/7 10:50
 * 屏蔽服务
 */
public interface ShieldService {

//    public void shield(MsgTask msgTask);
    public MsgTask shield(MsgTask msgTask);
}
