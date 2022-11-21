package com.INGSW.NaTour.Model;

import java.io.Serializable;
import java.util.Date;

public class MapPoint implements Serializable {

    private Date date=null;
    private Double longitude;
    private Double latitude;




    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public MapPoint(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public MapPoint(Date date, Double longitude, Double latitude) {
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "MapPoint{" +
                "date=" + date +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
