package com.yyyy.multisend.handler.deduplication.service;

import com.yyyy.multisend.handler.deduplication.builder.DeduplicationParm;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author isADuckA
 * @Date 2023/4/26 15:19
 *
 */
@Service
public interface DeduplicationService {

//    public Set<String> dedupService(DeduplicationParm deduplicationParm, Properties props);
    public Map<String, Map<String,String>> dedupService(DeduplicationParm deduplicationParm, Properties props);
}
