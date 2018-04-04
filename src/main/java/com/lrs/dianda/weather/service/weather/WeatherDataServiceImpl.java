package com.lrs.dianda.weather.service.weather;

import com.alibaba.fastjson.JSON;
import com.lrs.dianda.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by lrs on 2018/3/28.
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    public static final String WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private RestTemplate restTemplate;

    // 此对象用于储存json字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 此对象用于储存对象
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public WeatherResponse getWeatherDataByCityId(String cityId) throws Exception {
        String url = WEATHER_URL + "citykey=" + cityId;
        return doGetWeather(url);
    }

    // 从redis中获取json数据字符串
    private WeatherResponse doGetWeather(String url) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        if (stringRedisTemplate.hasKey(url)) {
            return JSON.parseObject(getWeatherDataFromRedis(url), WeatherResponse.class);
        } else {
            String weather = getWeatherDataFromClient(url);
            if (weather != null) {
                // 10秒时间销毁数据
                ops.set(url, weather, 10L, TimeUnit.SECONDS);
                return JSON.parseObject(weather, WeatherResponse.class);
            } else {
                return null;
            }
        }
    }

    // 从redis中获取json数据对象
    private WeatherResponse doGetWeather2(String url) {
        if (redisTemplate.hasKey(url)) {
            return (WeatherResponse) redisTemplate.boundValueOps(url).get();
        } else {
            String json = getWeatherDataFromClient(url);
            WeatherResponse weatherResponse = JSON.parseObject(json, WeatherResponse.class);
            if (weatherResponse != null) {
                redisTemplate.opsForValue().set(url, weatherResponse, 10, TimeUnit.SECONDS);
                return weatherResponse;
            } else {
                return null;
            }
        }
    }

    // 从redis中获取数据
    private String getWeatherDataFromRedis(String url) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        return ops.get(url);
    }

    // 从天气系统中获取数据
    private String getWeatherDataFromClient(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public WeatherResponse getWeatherDataByCityName(String cityName) throws Exception {
        String url = WEATHER_URL + "city=" + cityName;
        return doGetWeather2(url);
    }

}
