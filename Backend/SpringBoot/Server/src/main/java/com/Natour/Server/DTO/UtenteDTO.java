package com.Natour.Server.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDTO {

    private String username;
    private String email;
    private String descrizione;
    private String propic = "https://cdn-icons-png.flaticon.com/512/1177/1177568.png";
    private boolean admin=false;


}
