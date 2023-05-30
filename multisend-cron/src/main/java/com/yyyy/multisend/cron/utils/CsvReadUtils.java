package com.yyyy.multisend.cron.utils;

import cn.hutool.core.text.csv.CsvReadConfig;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRowHandler;
import cn.hutool.core.text.csv.CsvUtil;
import com.google.common.base.Throwables;
import com.yyyy.multisend.cron.po.CountFileRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author isADuckA
 * @Date 2023/5/9 18:40
 * csv 文件读取工具类
 */
@Slf4j
public class CsvReadUtils {

    /**
     * 接收者的id
     */
    public static String RECEIVER="receiver";

    /**
     * 读取csv文件的行数
     * @param targetPath
     * @param countFileRow
     * @return
     */
    public static Long getRowCountOfCsv(String targetPath, CountFileRow countFileRow){
        try {
            //这出问题
            CsvReader reader = CsvUtil.getReader(new FileReader(targetPath), new CsvReadConfig().setContainsHeader(true));
            reader.read(countFileRow);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return countFileRow.getRow();
    }

    /**
     * 读取每一行的参数
     * @param targetPath
     * @param csvRowHandler
     */
    public static  void getCsvRow(String targetPath, CsvRowHandler csvRowHandler){
        try {
            // 把首行当做是标题，获取reader
            CsvReader reader = CsvUtil.getReader(new FileReader(targetPath),
                    new CsvReadConfig().setContainsHeader(true));
            reader.read(csvRowHandler);
        } catch (Exception e) {
            log.error("ReadFileUtils#getCsvRow fail!{}", Throwables.getStackTraceAsString(e));

        }
    }

    /**
     * 处理参数，将原来的receiver参数去除
     * @param parm
     * @return
     */
    public static Map<String,String> dealParm(Map<String,String> parm){
        if(parm==null){
            return null;
        }
        Map<String,String> resultParm=new HashMap<>();
        for(String key:parm.keySet()){
            if(!RECEIVER.equals(key)){
                resultParm.put(key,parm.get(key));
            }
        }
        return resultParm;
    }
}
