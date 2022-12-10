package com.Natour.Server.Service;

import com.Natour.Server.DTO.OpinioniDTO;
import com.Natour.Server.Entity.Opinioni;
import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.OpinioniRepository;
import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpinioniService {

    @Autowired
    private ModelMapper modelMapperOpinioni;
    @Autowired
    private OpinioniRepository opinioniRepository;

    @Autowired
    private SentieriRepository sentieriRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Opinioni convertDTOToEntity(OpinioniDTO opinioneDTO){
        modelMapperOpinioni.getConfiguration().setAmbiguityIgnored(true);
        Opinioni opinioni = modelMapperOpinioni.map(opinioneDTO, Opinioni.class);
        return opinioni;
    }

    public Sentieri save(OpinioniDTO opinioneDTO) {
        Opinioni opinione = convertDTOToEntity(opinioneDTO);
        Sentieri sentiero = sentieriRepository.getById(opinioneDTO.getIdSentieri());
        Utente utente = utenteRepository.getById(opinioneDTO.getIdUtente());
        opinione.setSentiero(sentiero);
        opinione.setUtente(utente);
        opinioniRepository.save(opinione);
        return updateSentieroAverage(opinione.getSentiero().getId());
    }

    public Sentieri updateSentieroAverage(Long id){
        Sentieri sentiero = sentieriRepository.getById(id);
        List<Opinioni> opinioniList = opinioniRepository.findAllListBysentieroId(id);

        int difficolta = 0, durata = 0;

        for(Opinioni o: opinioniList){
            durata += o.getDurata();
            difficolta += o.getDifficolta();
        }

        durata /= opinioniList.size();
        difficolta /= opinioniList.size();


        sentiero.setDurata(durata);
        sentiero.setDifficolta(difficolta);

        Sentieri sentieroUpdated = sentieriRepository.save(sentiero);
        System.out.println(sentieroUpdated);
        return sentieroUpdated;
    }
}
