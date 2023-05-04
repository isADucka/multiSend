package com.yyyy.multisend.handler.deduplication.parmHander;

import com.yyyy.multisend.common.povo.vo.common.Constant;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.deduplication.builder.DeduplicationParm;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/27 19:01
 * 构建key
 */
@Service
public class ConstructKey {


    /**
     * 构建多个参数
     * @param parm
     * @return
     */
    public Set<String> listKeys(DeduplicationParm parm){

        MsgTask msgTask = parm.getMsgTask();
        Set<String> keys=new HashSet<>();
        Set<String> receiver = msgTask.getReceiver();
        for (String user:receiver){
            String key = singleKey(parm.getDeduplicationType(), user, msgTask.getReceiverType(), msgTask.getModelId());
            keys.add(key);
        }
        return keys;
    }

    /**
     * 用于构建参数
     * key=去重类型_发送对象_接收者类型_发送模板
     * eg：10_luck@qq.com_1004_8;
     * @return
     */
    public String singleKey(Integer deduplicationType,String receiver,Integer receiverType,long modleId){
        return  deduplicationType+ Constant.SPACER+
                receiver+Constant.SPACER+
                receiverType+Constant.SPACER+
                modleId;
    }
}

