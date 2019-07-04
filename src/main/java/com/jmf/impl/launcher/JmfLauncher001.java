package com.jmf.impl.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.JavaObjectInputStreamAccess;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 10:06
 */
public class JmfLauncher001 extends AbstractLauncher {
    @Autowired
    JobLauncher jobLauncher;
    @Resource(name = "job001")
    Job job;

    @Override
    public void launch() {
        System.out.println(Thread.currentThread().getName() + "--001");
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters();
            JobExecution run = jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
