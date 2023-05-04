package com.yyyy.multisend.handler.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/4/23 23:31
 * 占位符工具
 */
@AllArgsConstructor
public class PlaceHolderUtils {
    private final static String PRE_PARM="{$";

    private final static String AFTER_PARM="}";

    private final static PropertyPlaceholderHelper PLACEHODLER_HELPER=new PropertyPlaceholderHelper(PRE_PARM,AFTER_PARM);

    /**
     * 解决占位符问题
     * @param template
     * @param tempMap
     * @return
     */
    public static String replace(String template, Map<String,String> tempMap){
        String result;
        try{
          result = PLACEHODLER_HELPER.replacePlaceholders(template, p -> tempMap.get(p));
            System.out.println("结果1"+result);
        }catch (NullPointerException e){
            //这里找不到应该会抛出异常，空指针异常，先在这写死
            throw e;
        }
        return result;
    }
}
