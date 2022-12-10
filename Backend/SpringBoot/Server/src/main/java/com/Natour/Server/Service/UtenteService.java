package com.Natour.Server.Service;

import com.Natour.Server.DTO.UtenteDTO;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Exception.UsernameAlreadyExistsException;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private ModelMapper modelMapperUtente;

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente convertDTOToEntity(UtenteDTO utenteDTO){
        Utente utente = modelMapperUtente.map(utenteDTO, Utente.class);
        return utente;
    }

    public List<Utente> getAllUtenti(){
        return (List<Utente>) utenteRepository.findAll();
    }

    public void saveUtente(UtenteDTO newutenteDTO) throws UsernameAlreadyExistsException {
        Utente utente = convertDTOToEntity(newutenteDTO);
        List<Utente> utenti = utenteRepository.findAll();
        for (Utente u : utenti) {
            if (utente.getUsername().equalsIgnoreCase(u.getUsername())) {
                throw new UsernameAlreadyExistsException();
            }
        }
        utenteRepository.save(utente);
    }

    public Utente getUtenteByUsername(String username) {
        return utenteRepository.findAllByUsername(username);
    }
}
