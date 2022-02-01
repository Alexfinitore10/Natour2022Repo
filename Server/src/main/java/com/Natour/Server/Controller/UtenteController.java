package com.Natour.Server.Controller;

import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.UtenteRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
public class UtenteController {

    private final UtenteRepository utenterepository;

    public UtenteController(UtenteRepository repository) {
        utenterepository = repository;
    }

    @GetMapping("/utenti")
    List<Utente> getUtenti(){
        return (List<Utente>) utenterepository.findAll();
    }

    @GetMapping("/utenti/{id}")
    Utente getUtente(@PathVariable Long id){
        return utenterepository.findById(id).orElseThrow();
    }

    @PostMapping("/newutenti")
    Utente createUtente(@RequestBody Utente newUtente){
        return utenterepository.save(newUtente);
    }



}
