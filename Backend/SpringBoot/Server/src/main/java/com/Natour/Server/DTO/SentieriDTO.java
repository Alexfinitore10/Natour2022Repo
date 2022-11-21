package com.Natour.Server.DTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentieriDTO {

    //Attributi della classe Sentieri
    private String nome;
    private String descrizione;
    private int durata;
    private int difficolta;
    private boolean disabile;
    private String località;
    private Long idPropietario;
    private List<GeoPointDTO> tracciato;
    private String immagine = "https://i.ibb.co/42VKNxz/media-1.png";

    @Override
    public String toString() {
        return "SentieriDTO{" +
                "nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", durata=" + durata +
                ", difficolta=" + difficolta +
                ", disabile=" + disabile +
                ", località='" + località + '\'' +
                ", idPropietario=" + idPropietario +
                ", tracciato=" + tracciato +
                ", immagine='" + immagine + '\'' +
                '}';
    }
}
