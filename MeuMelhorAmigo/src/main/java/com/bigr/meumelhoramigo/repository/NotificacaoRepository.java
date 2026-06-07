package com.bigr.meumelhoramigo.repository;

import com.bigr.meumelhoramigo.modelo.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioDestinoId(Long usuarioId);

    List<Notificacao> findByUsuarioDestinoIdAndLida(Long usuarioId, boolean lida);
}