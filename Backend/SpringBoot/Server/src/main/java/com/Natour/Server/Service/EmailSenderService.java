package com.Natour.Server.Service;

import com.Natour.Server.DTO.EmailDTO;
import com.Natour.Server.Entity.Email;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.EmailRepository;
import com.Natour.Server.Repository.UtenteRepository;
import net.bytebuddy.build.Plugin;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void sendEmail(Email email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("natour21email@gmail.com");
        message.setTo(email.getToEmail());
        message.setText(email.getBody());
        message.setSubject(email.getSubject());
        try {
            javaMailSender.send(message);
            emailRepository.save(email);
        }catch (MailException e){
            e.printStackTrace();
        }
    }

    public void sendAllEmail(EmailDTO emailDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Email email = modelMapper.map(emailDTO, Email.class);
        Utente utente = utenteRepository.getById(emailDTO.getUtenteid());
        email.setUtente(utente);

        List<Utente> utenti = utenteRepository.findAll();

        SimpleMailMessage message = new SimpleMailMessage();
        for(Utente u: utenti){
            message.setFrom("natour21email@gmail.com");
            message.setTo(u.getEmail());
            message.setText(email.getBody());
            message.setSubject(email.getSubject());
            try {
                javaMailSender.send(message);
                emailRepository.save(email);
            }catch (MailException e){
                throw e;
            }
        }
    }
}
