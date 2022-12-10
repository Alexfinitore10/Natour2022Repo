package com.Natour.Server.Service;

import com.Natour.Server.DTO.GeoPointDTO;
import com.Natour.Server.DTO.SentieriDTO;
import com.Natour.Server.Entity.*;
import com.Natour.Server.Exception.GenericErrorException;
import com.Natour.Server.Repository.OpinioniRepository;
import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class SentieriService {

    @Autowired
    private SentieriRepository sentieriRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private OpinioniRepository opinioniRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Sentieri> getAllSentieri(){return (List<Sentieri>) sentieriRepository.findAll();}

    public Sentieri getSentieriById(Long id){
        return sentieriRepository.findById(id).orElseThrow();
    }

    public Sentieri saveSentieri(Sentieri sentiero){
        return sentieriRepository.save(sentiero);
    }

    public void saveSentiero(SentieriDTO sentieriDTO){
        Sentieri sentiero = modelMapper.map(sentieriDTO,Sentieri.class);
        Utente utente = utenteRepository.getById(sentieriDTO.getIdUtente());
        sentiero.setUtente(utente);

        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

        if(sentieriDTO.getTracciato() != null){
            for(GeoPointDTO dto: sentieriDTO.getTracciato()){
                GeoPoint g = modelMapper.map(dto,GeoPoint.class);
                g.setLatitude(dto.getLatitude());
                g.setLongitude(dto.getLongitude());
                g.setDate(Instant.now().toString());
                g.setSentiero(sentiero);
                geoPoints.add(g);
            }
        }

        sentiero.setGeopointSentiero(geoPoints);

        Sentieri sentiero_id = sentieriRepository.save(sentiero);
        Opinioni opinione = new Opinioni(sentiero.getDifficolta(),sentiero.getDurata(),sentiero_id,utente);
        opinioniRepository.save(opinione);
    }

    public List<Sentieri> getSentieriByUser(long user_id) {
       return sentieriRepository.findAllByutenteId(user_id);
    }

    public List<Sentieri> getSentieriByUserIdOrderAsc() {
        return sentieriRepository.findAllByOrderByIdDesc();
    }

    public List<Sentieri> getSentieriQuery(String località, Integer durata, Integer difficolta, Boolean disabile) {
        return sentieriRepository.getSentieriByQuery(località, durata, difficolta, disabile);
    }

    public List<FotoSentiero> getSentieroPhotoById(Long id) {
        Sentieri sentiero =  sentieriRepository.findById(id).orElseThrow();
        return sentiero.getFotoSentiero();
    }

    public void saveSentieroTracciato(List<GeoPointDTO> geoPointDTOS, Long id) {
        Sentieri sentieri = sentieriRepository.getById(id);

        System.out.println(geoPointDTOS.toString());

        List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
        for(GeoPointDTO dto: geoPointDTOS){
            GeoPoint g = modelMapper.map(dto,GeoPoint.class);
            g.setLatitude(dto.getLatitude());
            g.setLongitude(dto.getLongitude());
            g.setDate(Instant.now().toString());
            g.setSentiero(sentieri);
            geoPoints.add(g);
        }

        sentieri.setGeopointSentiero(geoPoints);
        sentieriRepository.save(sentieri);
    }

    public List<GeoPoint> getTracciatoSentieri(Long id) {
        Sentieri sentiero = sentieriRepository.getById(id);
        return sentiero.getGeopointSentiero();
    }

    public void deleteTracciato(Long id) throws GenericErrorException {
        if(sentieriRepository.existsById(id)){
            sentieriRepository.deleteById(id);
        }else {
            throw new GenericErrorException();
        }


    }

    public Sentieri update(Long id, SentieriDTO sentieriDTO) {
        Sentieri sentiero = sentieriRepository.getById(id);
        Utente utente = utenteRepository.getById(sentieriDTO.getIdUtente());

        Sentieri sentieroToUpdate = modelMapper.map(sentieriDTO,Sentieri.class);

        if(sentieroToUpdate.getNome() != null)
            sentiero.setNome(sentieroToUpdate.getNome());

        if(sentieroToUpdate.getDescrizione() != null)
            sentiero.setDescrizione(sentieroToUpdate.getDescrizione());

        if(sentieroToUpdate.getLocalità() != null)
            sentiero.setLocalità(sentieroToUpdate.getLocalità());

        sentiero.setDifficolta(sentieroToUpdate.getDifficolta());
        sentiero.setDurata(sentieroToUpdate.getDurata());
        sentiero.setDisabile(sentieroToUpdate.isDisabile());

        if(utente.isAdmin()){
            Date now = new Date();
            String date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ITALIAN).format(now);
            sentiero.setLastModified(date);
        }

        return sentieriRepository.save(sentiero);
    }
}
