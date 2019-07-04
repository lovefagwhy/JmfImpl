package com.jmf.impl.jobDao;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.item.ExecutionContext;

import java.util.Collection;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 14:49
 */
public class JmfExecutionContextDao implements ExecutionContextDao {
    @Override
    public ExecutionContext getExecutionContext(JobExecution jobExecution) {
        return null;
    }

    @Override
    public ExecutionContext getExecutionContext(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void saveExecutionContext(JobExecution jobExecution) {

    }

    @Override
    public void saveExecutionContext(StepExecution stepExecution) {

    }

    @Override
    public void saveExecutionContexts(Collection<StepExecution> collection) {

    }

    @Override
    public void updateExecutionContext(JobExecution jobExecution) {

    }

    @Override
    public void updateExecutionContext(StepExecution stepExecution) {

    }
}
