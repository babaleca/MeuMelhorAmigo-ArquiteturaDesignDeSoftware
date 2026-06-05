package com.bigr.meumelhoramigo.repository;

import com.bigr.meumelhoramigo.modelo.SolicitacaoAdocao;
import com.bigr.meumelhoramigo.modelo.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoAdocaoRepository extends JpaRepository<SolicitacaoAdocao, Long> {

    List<SolicitacaoAdocao> findByAnimalId(Long animalId);

    List<SolicitacaoAdocao> findByAdotanteId(Long adotanteId);

    boolean existsByAnimalIdAndAdotanteIdAndStatus(Long animalId, Long adotanteId, StatusAdocao status);

    List<SolicitacaoAdocao> findByAnimalIdAndStatus(Long animalId, StatusAdocao status);
}