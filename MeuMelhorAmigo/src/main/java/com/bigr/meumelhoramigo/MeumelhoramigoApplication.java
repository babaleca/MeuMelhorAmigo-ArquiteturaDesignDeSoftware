package com.bigr.meumelhoramigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeumelhoramigoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeumelhoramigoApplication.class, args);
	}

	// O seeding do admin é feito por DataInitializer.seedAdmin (que verifica
	// se o usuário já existe antes de inserir). Não duplicar aqui, senão
	// ocorre violação da constraint unique do e-mail.

}
