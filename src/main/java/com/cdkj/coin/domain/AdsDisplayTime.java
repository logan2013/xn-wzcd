package com.cdkj.coin.domain;

import com.cdkj.coin.dao.base.ABaseDO;

import java.io.Serializable;

public class AdsDisplayTime extends ABaseDO implements Serializable {
    private Integer id;

    private String adscode;

    private String week;

    private Integer startTime;

    private Integer endTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdscode() {
        return adscode;
    }

    public void setAdscode(String adscode) {
        this.adscode = adscode == null ? null : adscode.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}