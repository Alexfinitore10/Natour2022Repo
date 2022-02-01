package com.Natour.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class ServerApplication {

	@RequestMapping("/")
	@ResponseBody
	public String hello(@RequestParam(value="name",defaultValue="World") String name ){
		return String.format("Hello %s", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


}
