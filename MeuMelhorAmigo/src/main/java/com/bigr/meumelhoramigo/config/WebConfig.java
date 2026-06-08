package com.bigr.meumelhoramigo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * Expõe a pasta de uploads (onde as fotos dos animais são salvas) como
 * recurso estático acessível via URL "/uploads/**".
 *
 * Sem isso, o caminho gravado no campo "foto" (ex: /uploads/abc.jpg) retorna
 * 404 e a imagem aparece quebrada na tela.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Caminho absoluto da pasta de uploads, terminando com "/"
        String localPath = Paths.get(uploadDir).toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(localPath);
    }
}
