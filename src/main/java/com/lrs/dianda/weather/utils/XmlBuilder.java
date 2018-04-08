package com.lrs.dianda.weather.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by lrs on 2018/4/8.
 */
public class XmlBuilder {

    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Reader reader = new StringReader(xmlStr);
        Object xmlObject = unmarshaller.unmarshal(reader);
        reader.close();
        return xmlObject;
    }
}
