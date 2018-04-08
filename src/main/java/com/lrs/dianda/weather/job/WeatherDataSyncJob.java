package com.lrs.dianda.weather.job;

import com.lrs.dianda.weather.model.City;
import com.lrs.dianda.weather.service.city.CityDataService;
import com.lrs.dianda.weather.service.weather.WeatherDataService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * Created by lrs on 2018/4/8.
 */
public class WeatherDataSyncJob extends QuartzJobBean {

    @Autowired
    private CityDataService cityDataService;
    @Autowired
    private WeatherDataService weatherDataService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherDataSyncJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Weather data sync job. Start!");
        try {
            // 获取城市id列表
            List<City> cities = cityDataService.queryCityList();
            // 遍历城市id获取天气信息
            int i = 1;
            for (City city : cities) {
                System.out.println(i++);
                String cityid = city.getCityCode();
                weatherDataService.syncWeatherDataByCityKey(cityid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Weather data sync job. End!");
    }
}
