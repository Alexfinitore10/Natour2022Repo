package com.INGSW.NaTour.Model.DTO;

public class OpinioneDTO {

    private int difficolta;
    private int durata;

    private Long idSentieri;
    private Long idUtente;

    public OpinioneDTO(){}

    public OpinioneDTO(int difficolta, int durata, Long idSentieri, Long idUtente) {
        this.difficolta = difficolta;
        this.durata = durata;
        this.idSentieri = idSentieri;
        this.idUtente = idUtente;
    }

    public int getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(int difficolta) {
        this.difficolta = difficolta;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Long getIdSentieri() {
        return idSentieri;
    }

    public void setIdSentieri(Long idSentieri) {
        this.idSentieri = idSentieri;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }
}
