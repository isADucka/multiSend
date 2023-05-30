package com.yyyy.multisend.cron.po;

import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvRowHandler;
import lombok.Data;
import lombok.Getter;

/**
 * @author isADuckA
 * @Date 2023/5/9 19:21
 * 获取文件的行数
 */
@Data
public class CountFileRow implements CsvRowHandler {

    private long row;

    @Override
    public void handle(CsvRow csvrow) {
        row++;
    }

    public long getRowSize() {
        return row;
    }
}