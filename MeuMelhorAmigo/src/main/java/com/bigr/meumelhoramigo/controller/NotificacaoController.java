package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.repository.UsuarioRepository;
import com.bigr.meumelhoramigo.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/notificacoes")
    public String listar(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (usuario == null) return "redirect:/login";

        model.addAttribute("notificacoes", notificacaoService.listarPorUsuario(usuario.getId()));
        return "notificacoes";
    }

    @PostMapping("/notificacoes/{id}/lida")
    public String marcarLida(@PathVariable Long id) {
        notificacaoService.marcarLida(id);
        return "redirect:/notificacoes";
    }
}