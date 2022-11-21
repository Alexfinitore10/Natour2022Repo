package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

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
    private Sentieri sentieriProprietario;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_utente", nullable = false, referencedColumnName = "utente_id")
    private Utente utenteProprietario;

    public Opinioni(int difficolta, int durata, Sentieri sentieriProprietario, Utente utenteProprietario) {
        this.difficolta = difficolta;
        this.durata = durata;
        this.sentieriProprietario = sentieriProprietario;
        this.utenteProprietario = utenteProprietario;
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
