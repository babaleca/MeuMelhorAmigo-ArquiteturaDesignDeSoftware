package com.bigr.meumelhoramigo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Responsavel por salvar as fotos enviadas (MultipartFile) na pasta de
 * uploads e devolver o caminho publico que sera guardado no campo
 * "foto" do Animal.
 */
@Service
public class FileStorageService {

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    /**
     * Salva o arquivo recebido e retorna o caminho publico (ex: /uploads/abc.jpg).
     * Retorna null se nenhum arquivo foi enviado.
     */
    public String salvar(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }
        try {
            Path pasta = Paths.get(uploadDir);
            Files.createDirectories(pasta);

            String original = StringUtils.cleanPath(
                    arquivo.getOriginalFilename() == null ? "foto" : arquivo.getOriginalFilename());
            String extensao = "";
            int ponto = original.lastIndexOf('.');
            if (ponto >= 0) {
                extensao = original.substring(ponto);
            }
            String nomeArquivo = UUID.randomUUID() + extensao;

            Path destino = pasta.resolve(nomeArquivo);
            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + nomeArquivo;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar a foto do animal", e);
        }
    }
}
