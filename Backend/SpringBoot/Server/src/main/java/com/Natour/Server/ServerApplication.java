package com.Natour.Server;

import com.Natour.Server.Repository.SentieriRepository;
import com.Natour.Server.Repository.UtenteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class ServerApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@RequestMapping("/")
	@ResponseBody
	public String helloWorld(){
		return new String("Hello World!");
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
