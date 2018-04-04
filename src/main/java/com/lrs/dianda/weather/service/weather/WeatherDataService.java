package com.lrs.dianda.weather.service.weather;

import com.lrs.dianda.weather.model.WeatherResponse;

/**
 * Created by lrs on 2018/3/28.
 */
public interface WeatherDataService {

    WeatherResponse getWeatherDataByCityId(String cityId) throws Exception;

    WeatherResponse getWeatherDataByCityName(String cityName) throws Exception;
}
