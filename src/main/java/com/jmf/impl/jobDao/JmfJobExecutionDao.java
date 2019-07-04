package com.jmf.impl.jobDao;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.repository.dao.JobExecutionDao;

import java.util.List;
import java.util.Set;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 14:48
 */
public class JmfJobExecutionDao implements JobExecutionDao {
    @Override
    public void saveJobExecution(JobExecution jobExecution) {

    }

    @Override
    public void updateJobExecution(JobExecution jobExecution) {

    }

    @Override
    public List<JobExecution> findJobExecutions(JobInstance jobInstance) {
        return null;
    }

    @Override
    public JobExecution getLastJobExecution(JobInstance jobInstance) {
        return null;
    }

    @Override
    public Set<JobExecution> findRunningJobExecutions(String s) {
        return null;
    }

    @Override
    public JobExecution getJobExecution(Long aLong) {
        return null;
    }

    @Override
    public void synchronizeStatus(JobExecution jobExecution) {

    }
}
