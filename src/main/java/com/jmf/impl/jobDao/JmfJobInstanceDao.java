package com.jmf.impl.jobDao;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;

import java.util.List;
import java.util.Set;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 14:46
 */
public class JmfJobInstanceDao implements JobInstanceDao {
    @Override
    public JobInstance createJobInstance(String s, JobParameters jobParameters) {
        return null;
    }

    @Override
    public JobInstance getJobInstance(String s, JobParameters jobParameters) {
        return null;
    }

    @Override
    public JobInstance getJobInstance(Long aLong) {
        return null;
    }

    @Override
    public JobInstance getJobInstance(JobExecution jobExecution) {
        return null;
    }

    @Override
    public List<JobInstance> getJobInstances(String s, int i, int i1) {
        return null;
    }

    @Override
    public List<String> getJobNames() {
        return null;
    }

    @Override
    public List<JobInstance> findJobInstancesByName(String s, int i, int i1) {
        return null;
    }

    @Override
    public int getJobInstanceCount(String s) throws NoSuchJobException {
        return 0;
    }

    /**
     * Description:
     * Author:        LiuZhuang
     * Create Date:   2019/5/20 14:47
     */
    public static class JmfJobExecutionDao implements JobExecutionDao {
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
}
