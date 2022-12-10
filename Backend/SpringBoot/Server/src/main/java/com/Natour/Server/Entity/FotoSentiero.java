package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FotoSentiero {

    @Id
    @GeneratedValue
    private Long id;

    private String url;


    @JsonBackReference
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_sentieri", nullable = false, referencedColumnName = "sentieri_id")
    private Sentieri sentiero;

    @JsonManagedReference
    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_utente", nullable = false, referencedColumnName = "utente_id")
    private Utente utente;


}
