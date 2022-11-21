package com.Natour.Server.Controller;

import com.Natour.Server.DTO.GeoPointDTO;
import com.Natour.Server.DTO.SentieriDTO;
import com.Natour.Server.Entity.FotoSentiero;
import com.Natour.Server.Entity.GeoPoint;
import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Exception.GenericErrorException;
import com.Natour.Server.Service.SentieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SentieriController {

    @Autowired
    private SentieriService sentieriService;

    @GetMapping("/sentieri")
    List<Sentieri> getSentieri(){
        return sentieriService.getAllSentieri();
    }

    @GetMapping("/sentieri/{id}")
    Sentieri getSenteiro(@PathVariable Long id){
        return sentieriService.getSentieriById(id);
    }

    @GetMapping("/sentieri/{id}/photos")
    List<FotoSentiero> getSenteiroPhoto(@PathVariable Long id){
        return sentieriService.getSentieroPhotoById(id);
    }

    @PostMapping("/newsentieri")
    Sentieri createSentiero(@RequestBody Sentieri newSentiero){
        return sentieriService.saveSentieri(newSentiero);
    }

    @GetMapping("/sentieri/")
    List<Sentieri> getSentieriByUser(@RequestParam(value="user") long user_id){
        return sentieriService.getSentieriByUser(user_id);
    }

    @GetMapping("/filtersentieri")
    List<Sentieri> getSentieriByUser(@RequestParam(value="località",required = false) String località,@RequestParam(value="durata",required = false) Integer durata,@RequestParam(value="difficolta",required = false) Integer difficolta, @RequestParam(value="disabile",required = false) Boolean disabile){
        return sentieriService.getSentieriQuery(località, durata, difficolta, disabile);
    }

    @GetMapping("/sentieriorder")
    List<Sentieri> getSentieriByOrder(){
        return sentieriService.getSentieriByUserIdOrderAsc();
    }

    @PostMapping("/newsentiero")
    void createSentieroDTO(@RequestBody SentieriDTO newSentieroDTO){
        sentieriService.saveSentiero(newSentieroDTO);
    }


    @PostMapping("/sentieri/{id}/newtracciato")
    void createTracciatoSentiero(@RequestBody List<GeoPointDTO> geoPointDTOS, @PathVariable Long id){
        sentieriService.saveSentieroTracciato(geoPointDTOS,id);
    }

    @GetMapping("/sentieri/{id}/tracciato")
    List<GeoPoint> getTracciatoSentiero(@PathVariable Long id){
        return sentieriService.getTracciatoSentieri(id);
    }

    @DeleteMapping("/sentieri/delete/{id}")
    void deleteSentiero(@PathVariable Long id) throws GenericErrorException {
        sentieriService.deleteTracciato(id);
    }

    @PutMapping("/sentieri/update/{id}")
    Sentieri updateSentiero(@PathVariable Long id, @RequestBody SentieriDTO sentieriDTO){
        return sentieriService.update(id, sentieriDTO);
    }
}
