package com.lrs.dianda.weather.configuration;

import com.lrs.dianda.weather.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * Created by lrs on 2018/4/8.
 */
@Configuration
public class QuartzConfiguration {

    private static final int TIME = 1000*60*30;

    @Bean
    public JobDetail weatherDataSyncJobJobDetail() {
        // 指定作业目标
        return JobBuilder.newJob(WeatherDataSyncJob.class).storeDurably().withIdentity("weatherDataSyncJobDetail" + UUID.randomUUID().toString()).build();
    }

    /**
     * 定义一个触发器
     *
     * @return trigger
     */
    @Bean
    public Trigger weatherDataSyncJobTrigger() {
        // 定义一个2秒执行一次的时间表
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(TIME).repeatForever();
        // 触发器
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobJobDetail()).withSchedule(simpleScheduleBuilder).withIdentity("weatherDataSyncTrigger" + UUID.randomUUID().toString()).build();
    }
}
