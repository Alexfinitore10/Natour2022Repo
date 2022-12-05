package com.INGSW.NaTour.Model.DTO;

import com.INGSW.NaTour.Model.MapPoint;

import java.util.List;

public class SentieriDTO {

    private String nome;
    private String descrizione;
    private int durata;
    private int difficolta;
    private boolean disabile;
    private String località;
    private Long idUtente;
    private List<MapPoint> tracciato;

    public SentieriDTO(String nome, String descrizione, int durata, int difficolta, boolean disabile, String località, Long idUtente) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.durata = durata;
        this.difficolta = difficolta;
        this.disabile = disabile;
        this.località = località;
        this.idUtente = idUtente;
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

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public List<MapPoint> getTracciato() {
        return tracciato;
    }

    public void setTracciato(List<MapPoint> tracciato) {
        this.tracciato = tracciato;
    }




    @Override
    public String toString() {
        return "SentieriDTO{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", durata=" + durata +
                ", difficolta=" + difficolta +
                ", disabile=" + disabile +
                ", località='" + località + '\'' +
                ", idUtente=" + idUtente +
                ", tracciato=" + tracciato +
                '}';
    }
}
