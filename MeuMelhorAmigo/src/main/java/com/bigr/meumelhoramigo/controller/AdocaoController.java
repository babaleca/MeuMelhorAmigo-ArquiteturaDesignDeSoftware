package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.SolicitacaoAdocao;
import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.repository.AnimalRepository;
import com.bigr.meumelhoramigo.repository.UsuarioRepository;
import com.bigr.meumelhoramigo.service.AdocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/adocao/solicitar/{animalId}")
    public String formSolicitar(@PathVariable Long animalId, Model model) {
        animalRepository.findById(animalId).ifPresent(a -> model.addAttribute("animal", a));
        return "adocao-form";
    }

    @PostMapping("/adocao/solicitar/{animalId}")
    public String solicitar(@PathVariable Long animalId,
                            @RequestParam String motivacao,
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            Usuario adotante = usuarioRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
            adocaoService.solicitar(animalId, adotante, motivacao);
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação enviada com sucesso!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/animais/" + animalId;
    }

    @PostMapping("/adocao/aprovar/{id}")
    public String aprovar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adocaoService.aprovar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Adoção aprovada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao aprovar: " + e.getMessage());
        }
        return "redirect:/adocao/minhas-solicitacoes";
    }

    @PostMapping("/adocao/recusar/{id}")
    public String recusar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adocaoService.recusar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação recusada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao recusar: " + e.getMessage());
        }
        return "redirect:/adocao/minhas-solicitacoes";
    }

    @GetMapping("/adocao/minhas-solicitacoes")
    public String minhasSolicitacoes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (usuario == null) return "redirect:/login";

        List<SolicitacaoAdocao> recebidas = animalRepository.findByResponsavelId(usuario.getId())
                .stream()
                .flatMap(animal -> adocaoService.listarPorAnimal(animal.getId()).stream())
                .toList();

        List<SolicitacaoAdocao> enviadas = adocaoService.listarPorAdotante(usuario.getId());

        model.addAttribute("recebidas", recebidas);
        model.addAttribute("enviadas", enviadas);
        return "adocoes-lista";
    }
}