package com.Natour.Server.Service;

import com.Natour.Server.DTO.FotoSentieroDTO;
import com.Natour.Server.Entity.FotoSentiero;
import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.FotoSentieroRepository;
import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoSentieroService {

    @Autowired
    private SentieriRepository sentieriRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Autowired
    private FotoSentieroRepository fotoSentieroRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void saveFoto(FotoSentieroDTO fotoSentieroDTO){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        FotoSentiero foto = modelMapper.map(fotoSentieroDTO, FotoSentiero.class);
        Sentieri sentiero = sentieriRepository.getById(fotoSentieroDTO.getIdSentieri());
        Utente utente = utenteRepository.getById(fotoSentieroDTO.getIdUtente());
        foto.setSentiero(sentiero);
        foto.setUtente(utente);
        fotoSentieroRepository.save(foto);
    }

}
