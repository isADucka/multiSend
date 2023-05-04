package com.yyyy.multisend.handler.deduplication.service;

import com.yyyy.multisend.common.povo.vo.common.Constant;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.deduplication.builder.DeduplicationParm;
import com.yyyy.multisend.handler.deduplication.parmHander.ConstructKey;
import com.yyyy.multisend.handler.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author isADuckA
 * @Date 2023/4/27 11:47
 * 频次去重（eg:用户一天内只能收到某平台10条消息，超过这个数值就要执行去重）
 */
@Service
public class FrequencyDeduplicationService implements DeduplicationService{

    @Autowired
    private ConstructKey constructKey;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Set<String> dedupService(DeduplicationParm deduplicationParm, Properties props) {
        MsgTask msgTask = deduplicationParm.getMsgTask();
        //用于存储需要删除的key
        List<String> receiver = new LinkedList<>();
        //构建传进来的接收者参数
        Set<String> keys = constructKey.listKeys(deduplicationParm);
        //这个是从redis查出来的数据
        Map<String, String> mget= redisUtils.mget(keys);
        //存储需要更新的key
        Map<String,Integer> waitToUpdate=new HashMap<>();
        //再次构建key来
        for(String user:msgTask.getReceiver()){
            String key = constructKey.singleKey(deduplicationParm.getDeduplicationType(), user, msgTask.getReceiverType(), msgTask.getModelId());
            String value = mget.get(key);
            //超过阈值的则不管他
            if(value==null){
                //说明当前的keyb不存在，所以直接存为1
                waitToUpdate.put(key,1);
            }else{
                int num = Integer.parseInt(value);
                if(num<10){
                    //说明未满阈值，所以需要将这个值增加
                    waitToUpdate.put(key,num+1);
                }else {
                    //不然就是超过阈值，从receiver中删除
                    receiver.add(user);
                }
            }

        }
        deduplicationParm.getMsgTask().getReceiver().removeAll(receiver);
        //把剩下的value要更新到redis里面去，并且更新过期时间
        redisUtils.updateValue(waitToUpdate);
        return deduplicationParm.getMsgTask().getReceiver();


    }
}
