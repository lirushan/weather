package com.lrs.dianda.weather.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lrs on 2018/3/30.
 */
public class RequestUtil {

    public static String getRequestPostString(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            if (sb.toString().length() > 0) {
                return sb.toString();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
