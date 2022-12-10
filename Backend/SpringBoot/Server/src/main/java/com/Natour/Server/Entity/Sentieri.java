package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sentieri {

    @Id
    @GeneratedValue
    @Column(name = "sentieri_id")
    private Long id;

    private String nome;
    private String descrizione;
    private int durata;
    private int difficolta;
    private boolean disabile;
    private String località;
    private String immagine;

    private String lastModified=null;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonProperty("utente")
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente", nullable = false, referencedColumnName = "utente_id")
    private Utente utente;

    @JsonBackReference
    @OneToMany(mappedBy = "sentiero", cascade = CascadeType.ALL)
    private List<FotoSentiero> fotoSentiero;

    @JsonBackReference
    @OneToMany(mappedBy = "sentiero", cascade = CascadeType.ALL)
    private List<Opinioni> opinioniSentiero;

    @JsonBackReference
    @OneToMany(mappedBy = "sentiero", cascade = CascadeType.ALL)
    private List<GeoPoint> geopointSentiero;



    @Override
    public String toString() {
        return "Sentieri{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", durata=" + durata +
                ", difficolta=" + difficolta +
                ", disabile=" + disabile +
                ", località='" + località + '\'' +
                ", immagine='" + immagine + '\'' +
                ", utente=" + utente +
                ", opinioniSentiero=" + opinioniSentiero +
                '}';
    }
}
