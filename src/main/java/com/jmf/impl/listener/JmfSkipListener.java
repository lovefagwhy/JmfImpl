package com.jmf.impl.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/22 14:25
 */
@Component
public class JmfSkipListener implements SkipListener<Object, Object> {
    @Override
    @OnSkipInRead
    public void onSkipInRead(Throwable throwable) {
        System.out.println(throwable.fillInStackTrace());
    }

    @Override
    @OnSkipInWrite
    public void onSkipInWrite(Object o, Throwable throwable) {

    }

    @Override
    @OnSkipInProcess
    public void onSkipInProcess(Object o, Throwable throwable) {

    }
}
