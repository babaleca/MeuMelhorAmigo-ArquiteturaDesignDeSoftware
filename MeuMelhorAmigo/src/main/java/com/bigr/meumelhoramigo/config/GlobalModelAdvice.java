package com.bigr.meumelhoramigo.config;

import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.StatusAdocao;
import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.repository.AnimalRepository;
import com.bigr.meumelhoramigo.repository.NotificacaoRepository;
import com.bigr.meumelhoramigo.repository.SolicitacaoAdocaoRepository;
import com.bigr.meumelhoramigo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Injeta em todas as telas (para usuarios autenticados) os dados que o
 * cabeçalho compartilhado precisa: usuario atual, nº de notificacoes nao
 * lidas e nº de solicitacoes pendentes recebidas (badges do menu).
 */
@ControllerAdvice
public class GlobalModelAdvice {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private NotificacaoRepository notificacaoRepository;
    @Autowired private AnimalRepository animalRepository;
    @Autowired private SolicitacaoAdocaoRepository solicitacaoRepository;

    @ModelAttribute
    public void headerData(Authentication authentication, Model model) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof UserDetails principal)) {
            return;
        }

        Usuario usuario = usuarioRepository.findByEmail(principal.getUsername()).orElse(null);
        if (usuario == null) return;

        int unread = notificacaoRepository
                .findByUsuarioDestinoIdAndLida(usuario.getId(), false).size();

        int pendentes = 0;
        for (Animal animal : animalRepository.findByResponsavelId(usuario.getId())) {
            pendentes += solicitacaoRepository
                    .findByAnimalIdAndStatus(animal.getId(), StatusAdocao.PENDENTE).size();
        }

        model.addAttribute("currentUser", usuario);
        model.addAttribute("unreadCount", unread);
        model.addAttribute("pendingReceivedCount", pendentes);
    }
}
