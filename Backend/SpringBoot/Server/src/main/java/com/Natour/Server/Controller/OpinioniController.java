package com.Natour.Server.Controller;


import com.Natour.Server.DTO.OpinioniDTO;
import com.Natour.Server.DTO.UtenteDTO;
import com.Natour.Server.Entity.Opinioni;
import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Service.OpinioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpinioniController {

    @Autowired
    private OpinioniService opinioniService;

    @PostMapping("/newOpinioni")
    Sentieri createOpinioni(@RequestBody OpinioniDTO opinioneDTO){
        Sentieri sentiero = opinioniService.save(opinioneDTO);
        System.out.println(sentiero);
        return sentiero;
    }

}
