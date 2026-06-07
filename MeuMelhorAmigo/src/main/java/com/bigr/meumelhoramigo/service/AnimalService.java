package com.bigr.meumelhoramigo.service;

import com.bigr.meumelhoramigo.events.AnimalCadastradoEvent;
import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import com.bigr.meumelhoramigo.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Salva o animal e publica o evento AnimalCadastradoEvent para que
     * outros modulos (notificacoes, correspondencia) possam reagir.
     */
    public Animal salvar(Animal animal) {
        Animal salvo = animalRepository.save(animal);
        eventPublisher.publishEvent(new AnimalCadastradoEvent(this, salvo));
        return salvo;
    }

    public List<Animal> listarTodos() {
        return animalRepository.findAllByOrderByDataRegistroDesc();
    }

    public List<Animal> buscarPorFiltro(String especie, StatusAnimal status) {
        String especieFiltro = (especie != null && !especie.isBlank()) ? especie : null;
        return animalRepository.buscarPorFiltro(especieFiltro, status);
    }

    public Optional<Animal> buscarPorId(Long id) {
        return animalRepository.findById(id);
    }
}
