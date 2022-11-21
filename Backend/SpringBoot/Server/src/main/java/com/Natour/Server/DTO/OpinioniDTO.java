package com.Natour.Server.DTO;

import com.Natour.Server.Entity.Sentieri;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpinioniDTO {

    private int difficolta;
    private int durata;

    private Long idSentieri;
    private Long idUtente;


}
