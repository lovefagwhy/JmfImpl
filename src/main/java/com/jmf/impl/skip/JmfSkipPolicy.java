package com.jmf.impl.skip;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/22 15:10
 */
@Component
public class JmfSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
        return true;
    }
}
