package com.yyyy.multisend.handler.deduplication.builder;

import com.yyyy.multisend.common.enums.DeduplicationType;
import com.yyyy.multisend.handler.deduplication.service.ContentDeduplicationService;
import com.yyyy.multisend.handler.deduplication.service.DeduplicationService;
import com.yyyy.multisend.handler.deduplication.service.FrequencyDeduplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author isADuckA
 * @Date 2023/4/27 11:30
 * 初始化去重的顺序
 */
@Service
public class InitProcess {

    @Autowired
    private ContentDeduplicationService contentDeduplicationService;

    @Autowired
    private FrequencyDeduplicationService frequencyDeduplicationService;

    /**
     * 构建去重的顺序，显示内容去重，然后再频次去重，这样的原因是，如果内容去重结束了这个操作，频次去重也不用执行了
     * @return
     */
    @PostConstruct
    public Map<Integer,DeduplicationService> getDeduplicationService(){
        Map<Integer,DeduplicationService> map=new LinkedHashMap<>();
        map.put(DeduplicationType.CONTENT_DEDUP.getCode(),contentDeduplicationService);
        map.put(DeduplicationType.PFRE_DEDUP.getCode(),frequencyDeduplicationService);
        return  map;
    }
}
