package com.jmf.impl;

import com.jmf.impl.config.DatabaseConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling
@ComponentScan("com.jmf.impl.*")
@ServletComponentScan(basePackages = "com.jmf.impl.*")
@MapperScan("com.jmf.impl.*")
public class ImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImplApplication.class, args);
    }

}
