package com.Natour.Server.Controller;

import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class SentieriController {

    private final SentieriRepository sentierirepository;

    public SentieriController(SentieriRepository repository) {
        sentierirepository = repository;
    }

    @RequestMapping("/sentieri")
    List<Sentieri> getSentieri(){
        return (List<Sentieri>) sentierirepository.findAll();
    }

    @PostMapping("/newsentieri")
    Sentieri createSentieri(@RequestBody Sentieri newSentiero){
        return sentierirepository.save(newSentiero);
    }

}
