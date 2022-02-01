package com.Natour.Server.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Utente {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Sentieri> paths;


    public Utente(String username, String email, ArrayList<String> amici) {
        this.username = username;
        this.email = email;
    }

    public Utente(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Utente() {}


    public Set<Sentieri> getPaths() {
        return paths;
    }

    public void setPaths(Set<Sentieri> paths) {
        this.paths = paths;
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

}
