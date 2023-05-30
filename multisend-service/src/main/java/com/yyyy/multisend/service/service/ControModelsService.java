package com.yyyy.multisend.service.service;


import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;

import java.util.List;

/**
 * @author isADuckA
 * @Date 2023/4/19 19:07
 * 保存模板的service
 */
public interface ControModelsService {
     Result saveModel(MessageModel messageModel);

     Result deleteModel(List<Long> modelId);
}
