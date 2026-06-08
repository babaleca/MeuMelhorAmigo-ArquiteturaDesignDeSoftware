package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import com.bigr.meumelhoramigo.repository.AnimalRepository;
import com.bigr.meumelhoramigo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping("/painel")
    public String painel(Model model) {
        List<Animal> animais = animalRepository.findAll();
        model.addAttribute("animais", animais);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("totalAnimais", animais.size());
        model.addAttribute("totalDisp",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.DISPONIVEL).count());
        model.addAttribute("totalPerdEnc",
                animais.stream().filter(a -> a.getStatus() == StatusAnimal.PERDIDO
                        || a.getStatus() == StatusAnimal.ENCONTRADO).count());
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        return "admin-painel";
    }

    @PostMapping("/usuarios/{id}/toggle-ativo")
    public String toggleAtivo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setAtivo(!usuario.isAtivo());
            usuarioRepository.save(usuario);
        });
        redirectAttributes.addFlashAttribute("sucesso", "Status do usuário atualizado.");
        return "redirect:/admin/painel";
    }
}