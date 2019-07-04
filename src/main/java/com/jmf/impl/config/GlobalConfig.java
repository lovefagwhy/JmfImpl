package com.jmf.impl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/17 9:34
 */
@Configuration
public class GlobalConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

    @Value("${jmf.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${jmf.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${jmf.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${jmf.executor.thread.name.prefix}")
    private String namePrefix;

    @Bean(name = "jmfExecutor")
    public ThreadPoolTaskExecutor jmfExecutor() {
        logger.info("start jmfExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
