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

    @GetMapping("/utentiDTO")
    List<UtenteDTO> getUtentiDTO(){
        return utenteService.getAllUtentiDTO();
    }

    /*@GetMapping("/utenti/{id}")
    Utente getUtente(@PathVariable Long id){
        return utenteService.getUtentiById(id);
    }*/

    @GetMapping("/utente/{username}")
    Utente getUtenteByUsername(@PathVariable String username){
        return utenteService.getUtenteByUsername(username);
    }

    @PostMapping("/newutenti")
    Utente createUtente(@RequestBody Utente newUtente){
        return utenteService.saveUtenti(newUtente);
    }

    @PostMapping("/newutente")
    void createUtente(@RequestBody UtenteDTO newUtenteDTO) throws UsernameAlreadyExistsException {
        utenteService.saveUtente(newUtenteDTO);
    }


    @GetMapping("/checkEmail")
    ResponseEntity<String> isEmailExists(){
        return ResponseEntity.status(465).body("Email exists");
    }




}
