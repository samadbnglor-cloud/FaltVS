package com.interviewscheduling.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor();
    }


    /*@Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return java.util.concurrent.Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("Async-VT-", 0).factory());
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Async-Notification-");
        executor.initialize();
        return executor;
    }*/
}
