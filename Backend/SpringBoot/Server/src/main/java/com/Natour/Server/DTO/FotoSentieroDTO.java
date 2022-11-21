package com.Natour.Server.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FotoSentieroDTO {

    private String url;
    private Long idSentieri;
    private Long idUtente;

}
