package com.jmf.impl.listener;

import com.jmf.impl.dao.JmfTaskConfigMapper;
import com.jmf.impl.dao.JmfTaskListMapper;
import com.jmf.impl.entity.JmfTaskConfig;
import com.jmf.impl.entity.JmfTaskList;
import com.jmf.impl.entity.JvmMsg;
import com.jmf.impl.launcher.AbstractLauncher;
import com.jmf.impl.task.JmfTaskController;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:   初始化监听器
 * Author:        LiuZhuang
 * Create Date:   2019/5/16 16:09
 */
@WebListener
public class SchedulingListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        JmfTaskListMapper jmfTaskListMapper = webApplicationContext.getBean("jmfTaskListMapper", JmfTaskListMapper.class);
        JmfTaskConfigMapper jmfConfigMapper = webApplicationContext.getBean("jmfTaskConfigMapper", JmfTaskConfigMapper.class);
        JmfTaskController jmfTaskController = webApplicationContext.getBean("jmfTaskController", JmfTaskController.class);
        List<JmfTaskList> jvm_rtxs = jmfTaskListMapper.selectByJvmId("JVM_Rtxx");
        JmfTaskConfig jmfTaskConfig;
        Map<String, AbstractLauncher> lanucherMap = new HashMap<>();
        JvmMsg jvmMsg;
        for (JmfTaskList jvm_rtx : jvm_rtxs) {
            jvmMsg = new JvmMsg();
            jmfTaskConfig = jmfConfigMapper.selectByPrimaryKey(jvm_rtx.getTaskId());
            jvmMsg.setJmfTaskConfig(jmfTaskConfig);
            jvmMsg.setJmfTaskList(jvm_rtx);
            jmfTaskController.getJvmMsgs().add(jvmMsg);
            //将jmf数据库中的launcher放入map中
            lanucherMap.put(jvm_rtx.getTaskId(), webApplicationContext.getBean(jvm_rtx.getTaskName(), AbstractLauncher.class));
        }
        jmfTaskController.setLanucherMap(lanucherMap);
        servletContext.setAttribute("jmfTaskController", jmfTaskController);
        jmfTaskController.startAll();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }
}
