package com.Natour.Server.Entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Sentieri {

    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descrizione;
    private String durata;
    private int difficolta;
    private boolean disabile;
    private ArrayList<String> photo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Utente user;

    public Sentieri(String nome, String descrizione, String durata, int difficolta, boolean disabile, ArrayList<String> photo, Utente user) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;
        this.difficolta = difficolta;
        this.disabile = disabile;
        this.photo = photo;
        this.user = user;

        //user.getPaths().add(this);
    }

    public Sentieri() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public int getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(int difficolta) {
        this.difficolta = difficolta;
    }

    public boolean isDisabile() {
        return disabile;
    }

    public void setDisabile(boolean disabile) {
        this.disabile = disabile;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }
}
