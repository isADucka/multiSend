package com.yyyy.multisend.handler.deduplication;

import cn.hutool.json.ObjectMapper;
import cn.hutool.setting.dialect.Props;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yyyy.multisend.common.povo.vo.common.Constant;
import com.yyyy.multisend.common.ssm.MsgTask;
import com.yyyy.multisend.handler.deduplication.builder.DeduplicationParm;
import com.yyyy.multisend.handler.deduplication.builder.InitProcess;
import com.yyyy.multisend.handler.deduplication.service.DeduplicationService;
import com.yyyy.multisend.handler.utils.GetConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/27 11:53
 * 实现去重的通道
 */
@Service
public class ExitDeduplicationService {

    @Autowired
    private InitProcess process;

//    @Autowired
//    private GetConfigProperties configProperties;

    private static final String PROPERTIES_PATH = "local.properties";
    private Props props = new Props(PROPERTIES_PATH, StandardCharsets.UTF_8);



    /**
     * 去重
     * @param msgTask
     * @return
     */
    public MsgTask deduplicationPorcess(MsgTask msgTask){
        //这里是不是得先获取配置文件里面是

//        Map<String, JSON> config = getConfig();
//        System.out.println("原始文件"+config);
        Map<Integer, DeduplicationService> deduplicationService = process.getDeduplicationService();
        //先封装参数
        DeduplicationParm deduplicationParm= DeduplicationParm.builder()
//                                                    .limit(config)
                                                    .msgTask(msgTask)
                                                    .build();

        Set<String> receivers=msgTask.getReceiver() ;
        //遍历两个去重。先是内容去重，然后再实现频次去重
        for (Integer code:deduplicationService.keySet()){
            //把去重类型赋值
            deduplicationParm.setDeduplicationType(code);
            //调用各个类型的去重
            receivers= deduplicationService.get(code).dedupService(deduplicationParm,props);
            System.out.println(receivers);
            //如果返回值的receiver是null,就不用进行频次去重了，直接结束这条链


            if(receivers.size()==0||receivers==null){
                break;
            }
            deduplicationParm.getMsgTask().setReceiver(receivers);

        }

        return msgTask;
    }

//    /**
//     * 获取配置文件
//     * @return
//     */
//    public Map<String,JSON> getConfig(){
//        //获取到配置文件里的值
//        String localProperties = configProperties.getLocalProperties(Constant.DEDUPLICATION_RULE_KEY, "{}");
//
//        //转为map
//        Map<String,JSON> map = JSON.parseObject(localProperties, Map.class);
//
//       return map;
//    }


}
