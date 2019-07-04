package com.jmf.impl.entity;

/**
 * Description:   jvm_info数据
 * Author:        LiuZhuang
 * Create Date:   2019/5/16 16:11
 */
public class JvmInfo {
    private String jvmId;
    private String IP;
    private int port;

    public JvmInfo() {
    }

    public String getJvmId() {
        return this.jvmId;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public String getIP() {
        return this.IP;
    }

    public void setIP(String iP) {
        this.IP = iP;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return this.jvmId + "," + this.IP + "," + this.port;
    }
}