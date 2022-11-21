package com.Natour.Server.Controller;

import com.Natour.Server.DTO.EmailDTO;
import com.Natour.Server.DTO.SentieriDTO;
import com.Natour.Server.Entity.Email;
import com.Natour.Server.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody Email email){
        emailSenderService.sendEmail(email);
    }

    @PostMapping("/sendallmail")
    public void sendAllEmail(@RequestBody EmailDTO emailDTO){
        emailSenderService.sendAllEmail(emailDTO);
    }
}
