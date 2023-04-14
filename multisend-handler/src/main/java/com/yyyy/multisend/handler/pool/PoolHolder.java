package com.yyyy.multisend.handler.pool;

import com.dtp.core.thread.DtpExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/14 0:36
 */
@Service
public class PoolHolder {


    public  Map<String, DtpExecutor> poolMap=new HashMap<>(33);

    ThreadConfig threadConfig=new ThreadConfig();

    /**
     * 真正初始化线程池
     */
    @PostConstruct
    public void initPoolMap(){
        System.out.println("init初始化");
        List<String> list = threadConfig.getList();
        System.out.println(list);
        for(String poolName:list){
            poolMap.put(poolName,PoolInit.getDtpExecutor(poolName));
        }
    }

    public DtpExecutor getPoolByName(String poolName){
        System.out.println(poolName+"是");
        return poolMap.get(poolName);
    }
}
