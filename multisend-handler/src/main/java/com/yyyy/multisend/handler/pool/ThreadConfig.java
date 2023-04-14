package com.yyyy.multisend.handler.pool;

import com.yyyy.multisend.common.enums.MsgType;
import com.yyyy.multisend.common.enums.ReceiverType;
import com.yyyy.multisend.common.enums.SendType;
import com.yyyy.multisend.common.ssm.MsgTask;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/4/13 18:48
 * 初始化各类线程的种类，一个种类就是一个线程池
 */

public class ThreadConfig {

    private List<String> list=new ArrayList<>();

    public List<String> getList() {
        for(ReceiverType receiverType:ReceiverType.values()){
            for(MsgType msgType:MsgType.values()){
                list.add(receiverType.getCode()+""+msgType.getType());
            }
        }
        return list;
    }
}
