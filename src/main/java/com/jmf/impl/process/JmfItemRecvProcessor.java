package com.jmf.impl.process;

import org.springframework.batch.item.ItemProcessor;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 10:29
 */
public class JmfItemRecvProcessor implements ItemProcessor {
    @Override
    public Object process(Object o) throws Exception {
        System.out.println("recv processor");
        return o;
    }
}
