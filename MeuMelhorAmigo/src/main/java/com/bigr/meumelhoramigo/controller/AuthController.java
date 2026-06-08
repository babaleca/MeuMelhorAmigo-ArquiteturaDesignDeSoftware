package com.bigr.meumelhoramigo.controller;

import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String senha,
                            Model model) {
        String nomeT = nome == null ? "" : nome.trim();
        String emailT = email == null ? "" : email.trim().toLowerCase();

        if (nomeT.length() < 3) {
            return erroCadastro(model, "Informe seu nome completo (mínimo 3 letras).", nomeT, emailT);
        }
        if (!emailT.matches("^\\S+@\\S+\\.\\S+$")) {
            return erroCadastro(model, "Informe um email válido.", nomeT, emailT);
        }
        if (senha == null || senha.length() < 6) {
            return erroCadastro(model, "A senha deve ter ao menos 6 caracteres.", nomeT, emailT);
        }
        if (usuarioService.buscarPorEmail(emailT).isPresent()) {
            return erroCadastro(model, "Já existe uma conta com este email.", nomeT, emailT);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nomeT);
        usuario.setEmail(emailT);
        usuario.setSenha(senha);
        usuario.setRole("ROLE_USER");
        usuario.setAtivo(true);
        usuarioService.cadastrar(usuario);

        return "redirect:/login?registered";
    }

    private String erroCadastro(Model model, String erro, String nome, String email) {
        model.addAttribute("erro", erro);
        model.addAttribute("nome", nome);
        model.addAttribute("email", email);
        return "cadastro";
    }
}
