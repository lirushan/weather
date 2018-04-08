package com.lrs.dianda.weather.service.weather;

import com.alibaba.fastjson.JSON;
import com.lrs.dianda.weather.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private static final int TIME = 60*30;

    // rest请求
    @Autowired
    private RestTemplate restTemplate;

    // 用于储存json字符串的redis
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

    // 用于储存对象的redis
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 根据城市id查询天气
    @Override
    public WeatherResponse getWeatherDataByCityId(String cityId) throws Exception {
        String url = WEATHER_URL + "citykey=" + cityId;
        return doGetWeather(url);
    }

    // 从redis中获取json数据对象
    private WeatherResponse doGetWeather(String url) {
        return (WeatherResponse) redisTemplate.boundValueOps(url).get();
    }

    // 从redis中获取数据
//    private String getWeatherDataFromRedis(String url) {
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        return ops.get(url);
//    }

    // 从天气系统中获取数据
    private String getWeatherDataFromClient(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return responseEntity.getBody();
        }
        return null;
    }

    // 根据城市名称查询天气
    @Override
    public WeatherResponse getWeatherDataByCityName(String cityName) throws Exception {
        String url = WEATHER_URL + "city=" + cityName;
        return doGetWeather(url);
    }

    // 同步redis
    @Override
    public void syncWeatherDataByCityKey(String cityKey) {
        String url = WEATHER_URL + "citykey=" + cityKey;
        saveWeatherData(url);
    }

    // 保存和覆盖redis的缓存数据
    private void saveWeatherData(String url) {
        String json = getWeatherDataFromClient(url);
        WeatherResponse weatherResponse = JSON.parseObject(json, WeatherResponse.class);
        if (weatherResponse != null) {
            redisTemplate.opsForValue().set(url, weatherResponse, TIME, TimeUnit.SECONDS);
        }
    }
}
