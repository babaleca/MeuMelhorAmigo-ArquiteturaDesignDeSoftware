package com.bigr.meumelhoramigo;

import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeumelhoramigoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeumelhoramigoApplication.class, args);
	}
	@Bean
	public CommandLineRunner initAdmin(UsuarioService usuarioService) {
		return args -> {
			Usuario admin = new Usuario();
			admin.setNome("Administrador");
			admin.setEmail("admin@meumelhoramigo.com");
			admin.setSenha("admin123");
			admin.setRole("ROLE_ADMIN");
			usuarioService.cadastrar(admin);
		};
	}

}
