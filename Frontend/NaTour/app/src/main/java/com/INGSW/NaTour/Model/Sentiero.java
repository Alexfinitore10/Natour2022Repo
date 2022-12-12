/*
    INGSW2122_N_34 FRANCESCO CICCARELLI N86003285, ALEX CIACCIARELLA N86003179
*/

package com.INGSW.NaTour.Model;

import java.io.Serializable;
import java.util.List;

public class Sentiero implements Serializable {

    private Long id;

    private String nome;
    private String descrizione;
    private int durata;
    private int difficolta;
    private boolean disabile;
    private String località;
    private String immagine;
    private String lastModified;

    private User utente;
    private List<FotoPercorso> fotoSentiero;
    private List<MapPoint> tracciato;


    public Sentiero(String nome, String descrizione, int durata, int difficolta, boolean disabile, String località, User utente) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;
        this.difficolta = difficolta;
        this.disabile = disabile;
        this.località = località;
        this.utente = utente;
    }

    public List<FotoPercorso> getFotoSentiero() {
        return fotoSentiero;
    }

    public void setFotoSentiero(List<FotoPercorso> fotoSentiero) {
        this.fotoSentiero = fotoSentiero;
    }

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

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
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

    public String getLocalità() {
        return località;
    }

    public void setLocalità(String località) {
        this.località = località;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public List<MapPoint> getTracciato() {
        return tracciato;
    }

    public void setTracciato(List<MapPoint> tracciato) {
        this.tracciato = tracciato;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "Sentiero{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", durata=" + durata +
                ", difficolta=" + difficolta +
                ", disabile=" + disabile +
                ", località='" + località + '\'' +
                ", immagine='" + immagine + '\'' +
                ", utente=" + utente +
                '}';
    }
}
