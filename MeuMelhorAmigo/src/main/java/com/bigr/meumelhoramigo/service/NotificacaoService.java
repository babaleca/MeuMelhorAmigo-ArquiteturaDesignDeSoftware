package com.bigr.meumelhoramigo.service;

import com.bigr.meumelhoramigo.modelo.Notificacao;
import com.bigr.meumelhoramigo.modelo.Usuario;
import com.bigr.meumelhoramigo.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao criarNotificacao(Usuario usuarioDestino, String tipo, String mensagem) {
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuarioDestino(usuarioDestino);
        notificacao.setTipo(tipo);
        notificacao.setMensagem(mensagem);
        notificacao.setLida(false);
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioDestinoId(usuarioId);
    }

    public void marcarLida(Long notificacaoId) {
        notificacaoRepository.findById(notificacaoId).ifPresent(n -> {
            n.setLida(true);
            notificacaoRepository.save(n);
        });
    }
}