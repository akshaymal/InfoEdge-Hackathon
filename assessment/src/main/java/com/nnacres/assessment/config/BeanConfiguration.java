package com.nnacres.assessment.config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    @Value("${executor.thread.core-pool:10}")
    private int corePoolSize;
    @Value("${executor.thread.max-pool:20}")
    private int maxPoolSize;
    @Value("${executor.queue.capacity:30}")
    private int queueCapacity;
    @Value("${executor.thread.timeout:720000}")
    private int threadTimeout;
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(threadTimeout);
        return threadPoolTaskExecutor;
    }
    @Bean
    public ExecutorService compilerExecutorService() {
        return new ExecutorServiceAdapter(threadPoolTaskExecutor());
    }
}