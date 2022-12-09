package com.INGSW.NaTour.Model;

import java.io.Serializable;

public class User implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String descrizione;

    private String propic;

    private Boolean admin;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(Long id, String username, String email, String descrizione, String propic, Boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.descrizione = descrizione;
        this.propic = propic;
        this.admin = admin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", propic='" + propic + '\'' +
                ", admin=" + admin +
                '}';
    }
}
