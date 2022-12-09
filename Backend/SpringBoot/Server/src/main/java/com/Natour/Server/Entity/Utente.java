package com.Natour.Server.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Utente {

    @Id
    @GeneratedValue
    @Column(name = "utente_id")
    private Long id;

    private String username;

    private String email;

    private String descrizione;

    private String propic;

    private boolean admin =false;


    @JsonBackReference
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Sentieri> listaSentieri;

    @JsonBackReference
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Opinioni> listaOpinioni;

    @JsonBackReference
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<FotoSentiero> fotoSentiero;

    @JsonBackReference
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Email> emailList;

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", propic='" + propic + '\'' +
                '}';
    }
}
