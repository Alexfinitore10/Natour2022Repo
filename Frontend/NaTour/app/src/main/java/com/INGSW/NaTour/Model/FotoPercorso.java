package com.INGSW.NaTour.Model;

public class FotoPercorso {


    private Long id;

    private String url;

    private Sentiero sentieriProprietario;


    public FotoPercorso(){}

    public FotoPercorso(Long id, String url, Sentiero sentieriProprietario) {
        this.id = id;
        this.url = url;
        this.sentieriProprietario = sentieriProprietario;
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

    public Sentiero getSentieriProprietario() {
        return sentieriProprietario;
    }

    public void setSentieriProprietario(Sentiero sentieriProprietario) {
        this.sentieriProprietario = sentieriProprietario;
    }

    @Override
    public String toString() {
        return "FotoPercorso{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", sentieriProprietario=" + sentieriProprietario +
                '}';
    }
}
