package com.bigr.meumelhoramigo.repository;

import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NOTA: este arquivo é um stub para a branch feature/observer compilar.
 * No merge final usar a versão completa da Pessoa 2 e adicionar o findByStatus.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByOrderByDataRegistroDesc();

    List<Animal> findByStatus(StatusAnimal status);

    List<Animal> findByResponsavelId(Long responsavelId);

    @Query("SELECT a FROM Animal a WHERE (:especie IS NULL OR a.especie = :especie) AND (:status IS NULL OR a.status = :status)")
    List<Animal> buscarPorFiltro(@Param("especie") String especie, @Param("status") StatusAnimal status);
}