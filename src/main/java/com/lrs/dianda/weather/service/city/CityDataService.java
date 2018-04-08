package com.lrs.dianda.weather.service.city;

import com.lrs.dianda.weather.model.City;

import java.util.List;

/**
 * Created by lrs on 2018/4/8.
 */
public interface CityDataService {

    List<City> queryCityList() throws Exception;
}
