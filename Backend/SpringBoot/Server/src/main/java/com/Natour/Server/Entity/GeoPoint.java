package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
public class GeoPoint {

    @Id
    @Column(name = "geopoint_id")
    private String date;

    private Double longitude;
    private Double latitude;

    @JsonBackReference
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_sentieri", nullable = false, referencedColumnName = "sentieri_id")
    private Sentieri sentiero;

    public GeoPoint(){
        date = Instant.now().toString();
    }

    public GeoPoint(Double longitude, Double latitude) {
        this.date = Instant.now().toString();
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GeoPoint(Double longitude, Double latitude, Sentieri sentiero) {
        this.date = Instant.now().toString();
        this.longitude = longitude;
        this.latitude = latitude;
        this.sentiero = sentiero;
    }

}
