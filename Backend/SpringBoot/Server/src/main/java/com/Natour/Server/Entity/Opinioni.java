package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Opinioni{

    @Id
    @GeneratedValue
    private Long id;

    private int difficolta;
    private int durata;

    @JsonBackReference
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_sentieri", nullable = false, referencedColumnName = "sentieri_id")
    private Sentieri sentiero;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente", nullable = false, referencedColumnName = "utente_id")
    private Utente utente;

    public Opinioni(int difficolta, int durata, Sentieri sentiero, Utente utente) {
        this.difficolta = difficolta;
        this.durata = durata;
        this.sentiero = sentiero;
        this.utente = utente;
    }

    @Override
    public String toString() {
        return "Opinioni{" +
                "id=" + id +
                ", difficolta=" + difficolta +
                ", durata=" + durata +
                '}';
    }
}
