package com.jmf.impl.jobDao;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.StepExecutionDao;

import java.util.Collection;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 14:49
 */
public class JmfStepExecutionDao implements StepExecutionDao {
    @Override
    public void saveStepExecution(StepExecution stepExecution) {

    }

    @Override
    public void saveStepExecutions(Collection<StepExecution> collection) {

    }

    @Override
    public void updateStepExecution(StepExecution stepExecution) {

    }

    @Override
    public StepExecution getStepExecution(JobExecution jobExecution, Long aLong) {
        return null;
    }

    @Override
    public void addStepExecutions(JobExecution jobExecution) {

    }
}
