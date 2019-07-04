package com.jmf.impl.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/21 16:54
 */
public class JmfItemRecvReader implements ItemReader<Object> {
    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
