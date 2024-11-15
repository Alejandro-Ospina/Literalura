package com.alejandro.literalura;

import com.alejandro.literalura.Entities.AutorDTO;
import com.alejandro.literalura.Entities.BookDTO;
import com.alejandro.literalura.Entities.Models.Author;
import com.alejandro.literalura.Entities.ResultsDTO;
import com.alejandro.literalura.Implementations.AppMenu;
import com.alejandro.literalura.Implementations.LogicService;
import com.alejandro.literalura.Mappers.JsonMapper;
import com.alejandro.literalura.Services.APIService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RequiredArgsConstructor
public class LiteraluraApplication implements CommandLineRunner {

	private final AppMenu appMenu;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		appMenu.runApp();
	}
}