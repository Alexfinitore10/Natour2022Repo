package com.Natour.Server;

import com.Natour.Server.Entity.Card;
import com.Natour.Server.Entity.Utente;
import com.Natour.Server.Repository.CardRepository;
import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class ServerApplication {

	@Autowired
	UtenteRepository utenteRepository;

	@Autowired
	SentieriRepository sentieriRepository;

	@Autowired
	CardRepository cardRepository;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	@RequestMapping("/")
	@ResponseBody
	public String hello(@RequestParam(value="name",defaultValue="World") String name ){
		return String.format("Hello %s", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

/*
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		for (int i = 0; i < 20; i++){
			String val = String.valueOf(i);
			String lago = "Lago duria" + val;
			Card c1 = new Card( lago,"è un bel lago","50","Francesco","Napoli","https://www.cristianriva.it/wp-content/uploads/2020/05/019-cristian-riva-720x540.jpg","https://www.animeclick.it/immagini/personaggio/Kirito/cover/109315-Kirito-foto-alternativa.jpg",1,false);
			cardRepository.save(c1);
		}


		//Foto f = new Foto("IMG_20220314_194515_5bc003eb");
		//Foto f1 = new Foto("IMG_20210213_114212_1bf037ez");
		//Foto f2 = new Foto("IMG_20200011_191542_7iz027fw");

		Utente u = new Utente("Francesco","francy@gmail.com");
		utenteRepository.save(u);

		Sentieri s = new Sentieri("lago duria","è un lago","10",1,false,u);
		//s.getFotosentiero().add(f);
		//s.getFotosentiero().add(f1);
		//s.getFotosentiero().add(f2);
		sentieriRepository.save(s);

		Sentieri s1 = new Sentieri("vesuvio","è un cazzo di vulcano","40",3,true,u);
		sentieriRepository.save(s1);

		Sentieri s2 = new Sentieri("montagna","è un montagna che ti aspetti","120",5,false,u);
		sentieriRepository.save(s2);

		Utente u1 = new Utente("Giggio","Giggino@gmail.com");
		utenteRepository.save(u1);

		Sentieri s3 = new Sentieri("cazzi","è un cazzi ","180",10,false,u1);
		sentieriRepository.save(s3);

		Utente u2 = new Utente("Elvis","elbuonanno@gmail.com");
		utenteRepository.save(u2);



	}
*/







}
