package com.Natour.Server.Controller;

import com.Natour.Server.DTO.UtenteDTO;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Exception.UsernameAlreadyExistsException;
import com.Natour.Server.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/utenti")
    List<Utente> getUtenti(){
        return utenteService.getAllUtenti();
    }

    @GetMapping("/utente/{username}")
    Utente getUtenteByUsername(@PathVariable String username){
        return utenteService.getUtenteByUsername(username);
    }

    @PostMapping("/newutente")
    void createUtente(@RequestBody UtenteDTO newUtenteDTO) throws UsernameAlreadyExistsException {
        utenteService.saveUtente(newUtenteDTO);
    }





}
