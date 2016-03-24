package com.goertek.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table "CITY_AIR".
 */
public class CityAir implements Serializable{

    private Long id;
    private int ranking;
    /** Not-null value. */
    private String cityName;
    /** Not-null value. */
    private String provinceName;
    private int aqi;
    private String quality;
    /** Not-null value. */
    private String pm2_5;
    private String updateTime;

    public CityAir() {
    }

    public CityAir(Long id) {
        this.id = id;
    }

    public CityAir(Long id, int ranking, String cityName, String provinceName, int aqi, String quality, String pm2_5, String updateTime) {
        this.id = id;
        this.ranking = ranking;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.aqi = aqi;
        this.quality = quality;
        this.pm2_5 = pm2_5;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    /** Not-null value. */
    public String getCityName() {
        return cityName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /** Not-null value. */
    public String getProvinceName() {
        return provinceName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    /** Not-null value. */
    public String getPm2_5() {
        return pm2_5;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
