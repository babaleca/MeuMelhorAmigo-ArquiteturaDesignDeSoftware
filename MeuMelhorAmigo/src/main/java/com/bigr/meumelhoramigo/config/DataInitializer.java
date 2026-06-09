package com.bigr.meumelhoramigo.config;

import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cria um usuário administrador padrão na inicialização, caso ainda não exista.
 * Útil porque o banco é H2 em memória e zera a cada reinício.
 *
 * Login: admin@meumelhoramigo.com  /  Senha: admin123
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedAdmin(UsuarioService usuarioService) {
        return args -> {
            String email = "admin@meumelhoramigo.com";
            if (usuarioService.buscarPorEmail(email).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail(email);
                admin.setSenha("admin123"); // será criptografada pelo UsuarioService
                admin.setRole("ROLE_ADMIN");
                admin.setAtivo(true);
                usuarioService.cadastrar(admin);
                System.out.println(">>> Admin padrão criado: " + email + " / admin123");
            }
        };
    }
}
