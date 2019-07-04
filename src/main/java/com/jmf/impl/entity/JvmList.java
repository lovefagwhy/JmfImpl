package com.jmf.impl.entity;

/**
 * Description:   jvm_list数据
 * Author:        LiuZhuang
 * Create Date:   2019/5/16 16:11
 */
public class JvmList {
    private String taskId;
    private String jvmId;
    private String appName;
    private String taskName;
    private String methodName;
    private String triggerType;
    private String triggerPara;
    private String arguments;
    private String status = "UNKNOWN";
    private boolean scheduling;
    private String contextPath;
    private long elapsed = 0L;
    private String groupNo;
    private String groupNum;

    public JvmList() {
    }

    public long getElapsed() {
        return this.elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getScheduling() {
        return this.scheduling;
    }

    public void setScheduling(boolean scheduling) {
        this.scheduling = scheduling;
    }

    public void setRunInThisJvm(boolean r) {
    }

    public boolean getRunInThisJvm() {
        String jid = System.getProperty("JVMID");
        if (jid == null) {
            return false;
        } else {
            return jid.equals(this.jvmId);
        }
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getJvmId() {
        return this.jvmId;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getTriggerType() {
        return this.triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerPara() {
        return this.triggerPara;
    }

    public void setTriggerPara(String triggerPara) {
        this.triggerPara = triggerPara;
    }

    public String getArguments() {
        return this.arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getGroupNo() {
        return this.groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupNum() {
        return this.groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    @Override
    public String toString() {
        return this.taskId + "," + this.jvmId + "," + this.appName + "," + this.taskName + "," + this.methodName + "," + this.triggerType + "," + this.triggerPara + "," + this.scheduling + "," + this.arguments + "," + this.groupNo + "," + this.groupNum;
    }
}
