package com.example.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class CardApplication {
	public static void main(String[] args) {
		SpringApplication.run(CardApplication.class, args);
	}

}
