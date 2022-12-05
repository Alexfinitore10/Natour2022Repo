package com.INGSW.NaTour.Model;

public class FotoPercorso {


    private Long id;

    private String url;

    private Sentiero sentiero;


    public FotoPercorso(){}

    public FotoPercorso(Long id, String url, Sentiero sentiero) {
        this.id = id;
        this.url = url;
        this.sentiero = sentiero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Sentiero getSentiero() {
        return sentiero;
    }

    public void setSentiero(Sentiero sentiero) {
        this.sentiero = sentiero;
    }

    @Override
    public String toString() {
        return "FotoPercorso{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", sentieriProprietario=" + sentiero +
                '}';
    }
}
