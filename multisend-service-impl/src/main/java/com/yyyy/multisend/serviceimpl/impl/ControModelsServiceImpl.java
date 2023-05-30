package com.yyyy.multisend.serviceimpl.impl;
import com.yyyy.multisend.common.povo.vo.Result;
import com.yyyy.multisend.common.povo.po.MessageModel;
import com.yyyy.multisend.common.povo.vo.common.Constant;
import com.yyyy.multisend.dao.Mapper.MessageModelDao;
import com.yyyy.multisend.service.service.ControModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * @author isADuckA
 * @Date 2023/4/19 19:13
 * 保存模板
 */
@Service
public class ControModelsServiceImpl implements ControModelsService {

    @Autowired
    private MessageModelDao messageModelDao;

    @Override
    public Result saveModel(MessageModel messageModel) {
        if(messageModel.getModelId()==null){
            //如果是null,说明这个模板是新建的，所以要给他赋值一个新的modleId;
            messageModel.setModelId(messageModelDao.count()+1);
        }
        MessageModel save = messageModelDao.save(messageModel);

      if(save==null){
          return  new Result("保存模板失败");
      }
      return Result.SUCCESS("保存模板成功！");
    }

    //删除模板
    @Override
    public Result deleteModel(List<Long> modelId) {
        //软删除
        //先查出来后修改
        Iterable<MessageModel> allModles = messageModelDao.findAllById(modelId);
        for (MessageModel messageModel:allModles){
            messageModel.setIsDelete(Constant.TRUE);
        }
        messageModelDao.saveAll(allModles);

        return Result.SUCCESS("成功");
    }


}
