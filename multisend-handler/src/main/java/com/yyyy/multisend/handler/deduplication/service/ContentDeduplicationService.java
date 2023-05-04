package com.yyyy.multisend.handler.deduplication.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyyy.multisend.common.povo.vo.common.Constant;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.deduplication.builder.DeduplicationParm;
import com.yyyy.multisend.handler.deduplication.parmHander.ConstructKey;
import com.yyyy.multisend.handler.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/27 11:47
 * 内容去重（eg:实现五分钟内，对某用户发送相同的消息会执行去重操作）
 */
@Service
public class ContentDeduplicationService implements DeduplicationService{

    @Autowired
    private ConstructKey constructKey;
    @Autowired
    private RedisUtils redisUtils;

    private DefaultRedisScript<Long> redisScript;
    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    }

    @Override
    public Set<String> dedupService(DeduplicationParm deduplicationParm, Properties props) {
        MsgTask msgTask = deduplicationParm.getMsgTask();
        Set<String> receivers = msgTask.getReceiver();
        //生成一个雪花id，来作为一个value
        String snowId = String.valueOf(IdUtil.getSnowflakeNextId());
        //生成一个时间戳来作为分数
        String score= String.valueOf(System.currentTimeMillis());
        String property = props.getProperty(Constant.DEDUPLICATION + Constant.SPACER + deduplicationParm.getDeduplicationType() + Constant.POINT + Constant.TIME);

        deduplicationParm.setDeduplicationTime(Long.parseLong(property));
        deduplicationParm.setLimit(Integer.parseInt(props.getProperty(Constant.DEDUPLICATION + Constant.SPACER + deduplicationParm.getDeduplicationType() + Constant.POINT + Constant.NUM)));

        for(String user:receivers){
            String key = constructKey.singleKey(deduplicationParm.getDeduplicationType(), user, msgTask.getReceiverType(), msgTask.getModelId());
            System.out.println(key);
            Boolean aBoolean = redisUtils.execLimitLua(redisScript, key, String.valueOf(deduplicationParm.getDeduplicationTime()*1000), score,
                     deduplicationParm.getLimit().toString(),snowId);
            if(aBoolean){
                System.out.println("超过阈值");
                //如果是true,说明这个值已经超过阈值，所以要把这个用户删除
                receivers.remove(user);
            }else{
                System.out.println("还能添加进redis");
            }
        }
        //实现
        System.out.println("接收者"+receivers);
        return receivers;
    }
}
