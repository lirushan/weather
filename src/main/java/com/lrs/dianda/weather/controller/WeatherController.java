package com.lrs.dianda.weather.controller;

import com.alibaba.fastjson.JSONObject;
import com.lrs.dianda.weather.model.WeatherResponse;
import com.lrs.dianda.weather.service.weather.WeatherDataService;
import com.lrs.dianda.weather.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lrs on 2018/3/30.
 */
@RestController
@RequestMapping(value = "/weather")
public class WeatherController {
    @Autowired
    private WeatherDataService weatherDataService;

    @PostMapping(value = "/getWeatherDataByCityId")
    public WeatherResponse getWeatherDataByCityId(HttpServletRequest request) throws Exception {
        String json = RequestUtil.getRequestPostString(request);
        JSONObject rs = JSONObject.parseObject(json);
        return weatherDataService.getWeatherDataByCityId((String) rs.get("cityId"));
    }

    @PostMapping(value = "/getWeatherDataByCityName")
    public WeatherResponse getWeatherDataByCityName(HttpServletRequest request) throws Exception {
        String json = RequestUtil.getRequestPostString(request);
        JSONObject rs = JSONObject.parseObject(json);
        return weatherDataService.getWeatherDataByCityName((String) rs.get("cityName"));
    }
}
