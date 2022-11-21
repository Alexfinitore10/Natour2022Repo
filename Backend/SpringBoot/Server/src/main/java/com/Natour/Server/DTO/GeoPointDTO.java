package com.Natour.Server.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeoPointDTO {

    private Double longitude;
    private Double latitude;

    private Long idSentieri;

    public GeoPointDTO(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "GeoPointDTO{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", idSentieri=" + idSentieri +
                '}';
    }
}
