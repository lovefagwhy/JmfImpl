package com.jmf.impl.config;

import com.jmf.impl.listener.JmfSkipListener;
import com.jmf.impl.listener.JobListener;
import com.jmf.impl.process.JmfItemRecvProcessor;
import com.jmf.impl.process.JmfItemSendProcessor;
import com.jmf.impl.reader.JmfItemRecvReader;
import com.jmf.impl.reader.JmfItemSendReader;
import com.jmf.impl.skip.JmfSkipPolicy;
import com.jmf.impl.writer.JmfItemRecvWriter;
import com.jmf.impl.writer.JmfItemSendWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 9:54
 */
@Configuration
@EnableBatchProcessing
public class JobConfig {
    private static final Logger logger = LoggerFactory.getLogger(GlobalConfig.class);
    @Resource
    private JobListener jobListener;
    @Resource
    private JmfSkipListener jmfSkipListener;
    @Resource
    private JmfSkipPolicy jmfSkipPolicy;

    @Bean
    public BatchConfigurer configurer(@Qualifier("job") DataSource batchDataSource) {
        return new DefaultBatchConfigurer(batchDataSource);
    }

    /**
     * ItemReader定义,用来读取数据
     *
     * @return
     * @throws Exception
     */
    @Bean
    public ItemReader<Object> sendReader() throws Exception {
        JmfItemSendReader reader = new JmfItemSendReader();
        return reader;
    }

    /**
     * ItemProcessor定义，用来处理数据
     *
     * @return
     */
    @Bean
    public ItemProcessor<Object, Object> sendProcessor() {
        //使用我们自定义的ItemProcessor的实现CsvItemProcessor
        JmfItemSendProcessor processor = new JmfItemSendProcessor();
        return processor;
    }

    /**
     * ItemWriter定义，用来输出数据
     * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
     *
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Object> sendWriter(@Qualifier("job") DataSource dataSource) {
        JmfItemSendWriter writer = new JmfItemSendWriter();
        return writer;
    }


    /**
     * ItemReader定义,用来读取数据
     *
     * @return
     * @throws Exception
     */
    @Bean
    public ItemReader<Object> recvReader() throws Exception {
        JmfItemRecvReader reader = new JmfItemRecvReader();
        return reader;
    }

    /**
     * ItemProcessor定义，用来处理数据
     *
     * @return
     */
    @Bean
    public ItemProcessor<Object, Object> recvProcessor() {
        //使用我们自定义的ItemProcessor的实现CsvItemProcessor
        JmfItemRecvProcessor processor = new JmfItemRecvProcessor();
        return processor;
    }

    /**
     * ItemWriter定义，用来输出数据
     * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
     *
     * @param dataSource
     * @return
     */
    @Bean
    public ItemWriter<Object> recvWriter(@Qualifier("job") DataSource dataSource) {
        JmfItemRecvWriter writer = new JmfItemRecvWriter();
        return writer;
    }

    /**
     * JobRepository，用来注册Job的容器
     * jobRepositor的定义需要dataSource和transactionManager，Spring Boot已为我们自动配置了
     * 这两个类，Spring可通过方法注入已有的Bean
     *
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean(name = "jmfJobRepository")
    public JobRepository jobRepository(@Qualifier("job") DataSource dataSource, @Qualifier("txManager") PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean =
                new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.name());
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * JobLauncher定义，用来启动Job的接口
     *
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean(name = "jmfJobLauncher")
    public SimpleJobLauncher jobLauncher(@Qualifier("job") DataSource dataSource, @Qualifier("txManager") PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
        return jobLauncher;
    }

    /**
     * Job定义，我们要实际执行的任务，包含一个或多个Step
     *
     * @param jobBuilderFactory
     * @param stepRecv
     * @return
     */
    @Bean(name = "job001")
    public Job getJob001(JobBuilderFactory jobBuilderFactory, Step stepRecv) {
        return jobBuilderFactory.get("job001")
                .incrementer(new RunIdIncrementer())
                .flow(stepRecv)
                .end()
                .listener(jobListener)
                .build();
    }

    @Bean(name = "job002")
    public Job getJob002(JobBuilderFactory jobBuilderFactory, Step stepSend) {
        return jobBuilderFactory.get("job002")
                .incrementer(new RunIdIncrementer())
                .flow(stepSend)
                .end()
                .listener(jobListener)
                .build();
    }

    @Bean(name = "job003")
    public Job getJob003(JobBuilderFactory jobBuilderFactory, Step stepSend) {
        return jobBuilderFactory.get("job003")
                .incrementer(new RunIdIncrementer())
                .flow(stepSend)
                .end()
                .listener(jobListener)
                .build();
    }

    /**
     * step步骤，包含ItemReader，ItemProcessor和ItemWriter
     *
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step stepSend(StepBuilderFactory stepBuilderFactory, ItemReader<Object> sendReader, ItemWriter<Object> sendWriter,
                         ItemProcessor<Object, Object> sendProcessor) {
        return getStep(stepBuilderFactory, sendReader, sendWriter, sendProcessor, "stepSend");
    }

    /**
     * step步骤，包含ItemReader，ItemProcessor和ItemWriter
     *
     * @param stepBuilderFactory
     * @return
     */
    @Bean
    public Step stepRecv(StepBuilderFactory stepBuilderFactory, ItemReader<Object> recvReader, ItemWriter<Object> recvWriter,
                         ItemProcessor<Object, Object> recvProcessor) {
        return getStep(stepBuilderFactory, recvReader, recvWriter, recvProcessor, "stepRecv");
    }

    //绑定step
    public Step getStep(StepBuilderFactory stepBuilderFactory, ItemReader<Object> reader, ItemWriter<Object> writer, ItemProcessor<Object, Object> processor, String step) {
        return stepBuilderFactory
                .get(step)
                .<Object, Object>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
