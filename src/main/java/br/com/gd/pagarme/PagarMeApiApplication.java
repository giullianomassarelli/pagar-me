package br.com.gd.pagarme;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PagarMeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagarMeApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new org.modelmapper.ModelMapper();
	}

}
