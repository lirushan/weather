package com.lrs.dianda.weather.service.city;

import com.lrs.dianda.weather.model.City;
import com.lrs.dianda.weather.model.Citys;
import com.lrs.dianda.weather.utils.XmlBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by lrs on 2018/4/8.
 */
@Service
public class CityDataServiceImpl implements CityDataService {

    @Override
    public List<City> queryCityList() throws Exception {
        StringBuilder sb = new StringBuilder();
        Resource resource = new ClassPathResource("citylist.xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        Citys citys = (Citys) XmlBuilder.xmlStrToObject(Citys.class, sb.toString());
        return citys.getCityList();
    }
}
