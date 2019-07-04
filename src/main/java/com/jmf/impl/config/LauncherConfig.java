package com.jmf.impl.config;

import com.jmf.impl.launcher.AbstractLauncher;
import com.jmf.impl.launcher.JmfLauncher001;
import com.jmf.impl.launcher.JmfLauncher002;
import com.jmf.impl.launcher.JmfLauncher003;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 * Author:        LiuZhuang
 * Create Date:   2019/5/20 10:05
 */
@Configuration
public class LauncherConfig {
    @Bean(name = "rtxTest001Launcher")
    public AbstractLauncher getLauncher001() {
        return new JmfLauncher001();
    }

    @Bean(name = "rtxTest002Launcher")
    public AbstractLauncher getLauncher002() {
        return new JmfLauncher002();
    }

    @Bean(name = "rtxTest003Launcher")
    public AbstractLauncher getLauncher003() {
        return new JmfLauncher003();
    }
}
