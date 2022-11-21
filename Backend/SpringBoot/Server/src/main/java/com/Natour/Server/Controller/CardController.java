package com.Natour.Server.Controller;

import com.Natour.Server.Entity.Card;
import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.CardRepository;
import com.Natour.Server.Service.SentieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/card")
    List<Card> getCard(){
        return cardRepository.findAll();
    }

    @GetMapping("/card/{nomeId}")
    Card getCard(@PathVariable String nomeId){
        return (Card) cardRepository.findById(nomeId).orElseThrow();
    }

    @PostMapping("/newcard")
    Card createCard(@RequestBody Card newCard){
        return cardRepository.save(newCard);
    }

    @PutMapping("/editcard/{nomeId}")
    Card updateCard(@PathVariable String nomeId, @RequestBody Card newCard){
        Card cardEdited = cardRepository.findById(nomeId).orElseThrow();
        if(newCard.getNome() != null){
            cardEdited.setNome(newCard.getNome());
        }
        if(newCard.getDescrizione() != null){
            cardEdited.setDescrizione(newCard.getDescrizione());
        }
        if(newCard.getDurata() != null){
            cardEdited.setDurata(newCard.getDurata());
        }
        if(newCard.getDisabilità() != null){
            cardEdited.setDisabilità(newCard.getDisabilità());
        }
        if(newCard.getDifficoltà() != null){
            cardEdited.setDifficoltà(newCard.getDifficoltà());
        }
        if(newCard.getURLimmagine() != null){
            cardEdited.setURLimmagine(newCard.getURLimmagine());
        }
        if(newCard.getURLpropic() != null){
            cardEdited.setURLpropic(newCard.getURLpropic());
        }
        if(newCard.getUsername() != null){
            cardEdited.setUsername(newCard.getUsername());
        }
        if(newCard.getLocalità() != null){
            cardEdited.setLocalità(newCard.getLocalità());
        }

        return cardRepository.save(cardEdited);
    }

}
