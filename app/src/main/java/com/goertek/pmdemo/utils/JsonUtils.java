package com.goertek.pmdemo.utils;


import com.goertek.bean.CityAir;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-18
 */
public class JsonUtils {

    public static CityAir json2Entity(String jsonStr){

        CityAir cityAir = new CityAir();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject resJson = jsonObject.getJSONObject("result");
            cityAir.setRanking(resJson.getInt("Ranking"));
            cityAir.setCityName(resJson.getString("CityName"));
            cityAir.setProvinceName(resJson.getString("ProvinceName"));
            cityAir.setAqi(resJson.getInt("AQI"));
            cityAir.setPm2_5(resJson.getString("PM25"));
            cityAir.setQuality(resJson.getString("Quality"));
            cityAir.setUpdateTime(resJson.getString("UpdateTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityAir;
    }
}
