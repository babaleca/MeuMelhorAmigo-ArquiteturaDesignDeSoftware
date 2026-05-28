package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroForm() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(Usuario usuario) {
        usuarioService.cadastrar(usuario);
        return "redirect:/login";
    }
}
