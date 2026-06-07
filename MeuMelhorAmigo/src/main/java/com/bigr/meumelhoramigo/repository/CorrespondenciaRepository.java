package com.bigr.meumelhoramigo.repository;

import com.bigr.meumelhoramigo.modelo.Correspondencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorrespondenciaRepository extends JpaRepository<Correspondencia, Long> {

    List<Correspondencia> findByAnimalPerdidoId(Long animalPerdidoId);

    List<Correspondencia> findByAnimalEncontradoId(Long animalEncontradoId);
}