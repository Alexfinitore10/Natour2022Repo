package com.Natour.Server.Controller;

import com.Natour.Server.DTO.FotoSentieroDTO;
import com.Natour.Server.Service.FotoSentieroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FotoSentieriController {

    @Autowired
    private FotoSentieroService fotoSentieroService;

    @PostMapping("/newfoto")
    void createFoto(@RequestBody FotoSentieroDTO fotoSentieroDTO){
        fotoSentieroService.saveFoto(fotoSentieroDTO);
    }
}
