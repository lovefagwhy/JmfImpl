package com.jmf.impl.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 10:54
 */
public class JmfJobRepository implements JobRepository {
    private static final Log logger = LogFactory.getLog(SimpleJobRepository.class);
    private JobInstanceDao jobInstanceDao;
    private JobExecutionDao jobExecutionDao;
    private StepExecutionDao stepExecutionDao;
    private ExecutionContextDao ecDao;

    public JmfJobRepository() {
    }

    public JmfJobRepository(JobInstanceDao jobInstanceDao, JobExecutionDao jobExecutionDao, StepExecutionDao stepExecutionDao, ExecutionContextDao ecDao) {
        this.jobInstanceDao = jobInstanceDao;
        this.jobExecutionDao = jobExecutionDao;
        this.stepExecutionDao = stepExecutionDao;
        this.ecDao = ecDao;
    }

    @Override
    public boolean isJobInstanceExists(String jobName, JobParameters jobParameters) {
        return this.jobInstanceDao.getJobInstance(jobName, jobParameters) != null;
    }

    @Override
    public JobExecution createJobExecution(String jobName, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Assert.notNull(jobName, "Job name must not be null.");
        Assert.notNull(jobParameters, "JobParameters must not be null.");
        JobInstance jobInstance = this.jobInstanceDao.getJobInstance(jobName, jobParameters);
        ExecutionContext executionContext;
        if (jobInstance == null) {
            jobInstance = this.jobInstanceDao.createJobInstance(jobName, jobParameters);
            executionContext = new ExecutionContext();
        } else {
            List<JobExecution> executions = this.jobExecutionDao.findJobExecutions(jobInstance);
            Iterator var6 = executions.iterator();

            while (true) {
                if (!var6.hasNext()) {
                    executionContext = this.ecDao.getExecutionContext(this.jobExecutionDao.getLastJobExecution(jobInstance));
                    break;
                }

                JobExecution execution = (JobExecution) var6.next();
                if (!execution.isRunning() && !execution.isStopping()) {
                    BatchStatus status = execution.getStatus();
                    if (status == BatchStatus.UNKNOWN) {
                        throw new JobRestartException("Cannot restart job from UNKNOWN status. The last execution ended with a failure that could not be rolled back, so it may be dangerous to proceed. Manual intervention is probably necessary.");
                    }

                    if (execution.getJobParameters().getParameters().size() <= 0 || status != BatchStatus.COMPLETED && status != BatchStatus.ABANDONED) {
                        continue;
                    }

                    throw new JobInstanceAlreadyCompleteException("A job instance already exists and is complete for parameters=" + jobParameters + ".  If you want to run this job again, change the parameters.");
                }

                throw new JobExecutionAlreadyRunningException("A job execution for this job is already running: " + jobInstance);
            }
        }

        JobExecution jobExecution = new JobExecution(jobInstance, jobParameters, (String) null);
        jobExecution.setExecutionContext(executionContext);
        jobExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        this.jobExecutionDao.saveJobExecution(jobExecution);
        this.ecDao.saveExecutionContext(jobExecution);
        return jobExecution;
    }

    @Override
    public void update(JobExecution jobExecution) {
        Assert.notNull(jobExecution, "JobExecution cannot be null.");
        Assert.notNull(jobExecution.getJobId(), "JobExecution must have a Job ID set.");
        Assert.notNull(jobExecution.getId(), "JobExecution must be already saved (have an id assigned).");
        jobExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        this.jobExecutionDao.synchronizeStatus(jobExecution);
        this.jobExecutionDao.updateJobExecution(jobExecution);
    }

    @Override
    public void add(StepExecution stepExecution) {
        this.validateStepExecution(stepExecution);
        stepExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        this.stepExecutionDao.saveStepExecution(stepExecution);
        this.ecDao.saveExecutionContext(stepExecution);
    }

    public void addAll(Collection<StepExecution> stepExecutions) {
        Assert.notNull(stepExecutions, "Attempt to save a null collection of step executions");
        Iterator var2 = stepExecutions.iterator();

        while (var2.hasNext()) {
            StepExecution stepExecution = (StepExecution) var2.next();
            this.validateStepExecution(stepExecution);
            stepExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        }

        this.stepExecutionDao.saveStepExecutions(stepExecutions);
        this.ecDao.saveExecutionContexts(stepExecutions);
    }

    @Override
    public void update(StepExecution stepExecution) {
        this.validateStepExecution(stepExecution);
        Assert.notNull(stepExecution.getId(), "StepExecution must already be saved (have an id assigned)");
        stepExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        this.stepExecutionDao.updateStepExecution(stepExecution);
        this.checkForInterruption(stepExecution);
    }

    private void validateStepExecution(StepExecution stepExecution) {
        Assert.notNull(stepExecution, "StepExecution cannot be null.");
        Assert.notNull(stepExecution.getStepName(), "StepExecution's step name cannot be null.");
        Assert.notNull(stepExecution.getJobExecutionId(), "StepExecution must belong to persisted JobExecution");
    }

    @Override
    public void updateExecutionContext(StepExecution stepExecution) {
        this.validateStepExecution(stepExecution);
        Assert.notNull(stepExecution.getId(), "StepExecution must already be saved (have an id assigned)");
        this.ecDao.updateExecutionContext(stepExecution);
    }

    @Override
    public void updateExecutionContext(JobExecution jobExecution) {
        this.ecDao.updateExecutionContext(jobExecution);
    }

    @Nullable
    @Override
    public StepExecution getLastStepExecution(JobInstance jobInstance, String stepName) {
        List<JobExecution> jobExecutions = this.jobExecutionDao.findJobExecutions(jobInstance);
        List<StepExecution> stepExecutions = new ArrayList(jobExecutions.size());
        Iterator var5 = jobExecutions.iterator();

        while (var5.hasNext()) {
            JobExecution jobExecution = (JobExecution) var5.next();
            this.stepExecutionDao.addStepExecutions(jobExecution);
            Iterator var7 = jobExecution.getStepExecutions().iterator();

            while (var7.hasNext()) {
                StepExecution stepExecution = (StepExecution) var7.next();
                if (stepName.equals(stepExecution.getStepName())) {
                    stepExecutions.add(stepExecution);
                }
            }
        }

        StepExecution latest = null;
        Iterator var10 = stepExecutions.iterator();

        while (var10.hasNext()) {
            StepExecution stepExecution = (StepExecution) var10.next();
            if (latest == null) {
                latest = stepExecution;
            }

            if (latest.getStartTime().getTime() < stepExecution.getStartTime().getTime()) {
                latest = stepExecution;
            }

            if (latest.getStartTime().getTime() == stepExecution.getStartTime().getTime() && latest.getId() < stepExecution.getId()) {
                latest = stepExecution;
            }
        }

        if (latest != null) {
            ExecutionContext stepExecutionContext = this.ecDao.getExecutionContext(latest);
            latest.setExecutionContext(stepExecutionContext);
            ExecutionContext jobExecutionContext = this.ecDao.getExecutionContext(latest.getJobExecution());
            latest.getJobExecution().setExecutionContext(jobExecutionContext);
        }

        return latest;
    }

    @Override
    public int getStepExecutionCount(JobInstance jobInstance, String stepName) {
        int count = 0;
        List<JobExecution> jobExecutions = this.jobExecutionDao.findJobExecutions(jobInstance);
        Iterator var5 = jobExecutions.iterator();

        while (var5.hasNext()) {
            JobExecution jobExecution = (JobExecution) var5.next();
            this.stepExecutionDao.addStepExecutions(jobExecution);
            Iterator var7 = jobExecution.getStepExecutions().iterator();

            while (var7.hasNext()) {
                StepExecution stepExecution = (StepExecution) var7.next();
                if (stepName.equals(stepExecution.getStepName())) {
                    ++count;
                }
            }
        }

        return count;
    }

    private void checkForInterruption(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        this.jobExecutionDao.synchronizeStatus(jobExecution);
        if (jobExecution.isStopping()) {
            logger.info("Parent JobExecution is stopped, so passing message on to StepExecution");
            stepExecution.setTerminateOnly();
        }

    }

    @Nullable
    @Override
    public JobExecution getLastJobExecution(String jobName, JobParameters jobParameters) {
        JobInstance jobInstance = this.jobInstanceDao.getJobInstance(jobName, jobParameters);
        if (jobInstance == null) {
            return null;
        } else {
            JobExecution jobExecution = this.jobExecutionDao.getLastJobExecution(jobInstance);
            if (jobExecution != null) {
                jobExecution.setExecutionContext(this.ecDao.getExecutionContext(jobExecution));
                this.stepExecutionDao.addStepExecutions(jobExecution);
            }

            return jobExecution;
        }
    }

    @Override
    public JobInstance createJobInstance(String jobName, JobParameters jobParameters) {
        Assert.notNull(jobName, "A job name is required to create a JobInstance");
        Assert.notNull(jobParameters, "Job parameters are required to create a JobInstance");
        JobInstance jobInstance = this.jobInstanceDao.createJobInstance(jobName, jobParameters);
        return jobInstance;
    }

    @Override
    public JobExecution createJobExecution(JobInstance jobInstance, JobParameters jobParameters, String jobConfigurationLocation) {
        Assert.notNull(jobInstance, "A JobInstance is required to associate the JobExecution with");
        Assert.notNull(jobParameters, "A JobParameters object is required to create a JobExecution");
        JobExecution jobExecution = new JobExecution(jobInstance, jobParameters, jobConfigurationLocation);
        ExecutionContext executionContext = new ExecutionContext();
        jobExecution.setExecutionContext(executionContext);
        jobExecution.setLastUpdated(new Date(System.currentTimeMillis()));
        this.jobExecutionDao.saveJobExecution(jobExecution);
        this.ecDao.saveExecutionContext(jobExecution);
        return jobExecution;
    }
}
