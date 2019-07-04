package com.jmf.impl.task;

import com.jmf.impl.entity.JvmMsg;
import com.jmf.impl.launcher.AbstractLauncher;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/17 9:13
 */
@Component
public class JmfTaskController {
    @Resource(name = "jmfExecutor")
    private ThreadPoolTaskExecutor executor;
    private AbstractLauncher abstractLauncher;
    private TaskScheduler taskScheduler;
    private Map<String, AbstractLauncher> lanucherMap = new HashMap<>();

    private List<JvmMsg> jvmMsgs;

    public List<JvmMsg> getJvmMsgs() {
        if (jvmMsgs == null) {
            jvmMsgs = new ArrayList<>();
        }
        return jvmMsgs;

    }

    public void setJvmMsgs(List<JvmMsg> jvmMsgs) {
        this.jvmMsgs = jvmMsgs;
    }

    public void startAll() {
        for (JvmMsg jvmMsg : this.jvmMsgs) {
            final JvmMsg jvmMsg1 = jvmMsg;
            //if(taskScheduler==null){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    taskScheduler = new ConcurrentTaskScheduler();
                    try {
                        ScheduledMethodRunnable smr = new ScheduledMethodRunnable(lanucherMap.get(jvmMsg1.getJmfTaskList().getTaskId()), jvmMsg1.getJmfTaskList().getMethodName());
                        ScheduledFuture<?> schedule = taskScheduler.schedule(smr, new CronTrigger("0/10 * * * * ? "));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            //}
        }
    }

    public Map<String, AbstractLauncher> getLanucherMap() {
        return lanucherMap;
    }

    public void setLanucherMap(Map<String, AbstractLauncher> lanucherMap) {
        this.lanucherMap = lanucherMap;
    }
}
