package com.Natour.Server.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Card {

    @Id
    private String nome;
    private String descrizione, durata, username, località;
    private String URLimmagine, URLpropic;
    private Integer difficoltà;
    private Boolean disabilità;

    public Card(String nome, String descrizione, String durata, String username, String località, String URLimmagine, String URLpropic, Integer difficoltà, Boolean disabilità) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;
        this.username = username;
        this.URLimmagine = URLimmagine;
        this.URLpropic = URLpropic;
        this.difficoltà = difficoltà;
        this.disabilità = disabilità;
        this.località = località;
    }

    public Boolean getDisabilità() {
        return disabilità;
    }
}
