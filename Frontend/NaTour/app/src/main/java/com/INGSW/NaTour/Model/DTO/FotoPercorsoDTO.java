package com.INGSW.NaTour.Model.DTO;

public class FotoPercorsoDTO {

    private String url;
    private Long idSentieri;
    private Long idUtente;

    public FotoPercorsoDTO(String url, Long idSentieri, Long idUtente) {
        this.url = url;
        this.idSentieri = idSentieri;
        this.idUtente = idUtente;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
