package com.bigr.meumelhoramigo.service;

import com.bigr.meumelhoramigo.modelo.*;
import com.bigr.meumelhoramigo.repository.CorrespondenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorrespondenciaService {

    @Autowired
    private CorrespondenciaRepository correspondenciaRepository;

    @Autowired
    private AnimalRepository animalRepository;

    /**
     * Calcula o score de similaridade entre dois animais.
     * Especie: +40 pts, Raca: +30 pts, Cor: +20 pts, Local: +10 pts
     * Retorna um double de 0 a 100.
     */
    public double calcularScore(Animal a, Animal b) {
        double score = 0;

        if (a.getEspecie() != null && a.getEspecie().equalsIgnoreCase(b.getEspecie())) {
            score += 40;
        }
        if (a.getRaca() != null && a.getRaca().equalsIgnoreCase(b.getRaca())) {
            score += 30;
        }
        if (a.getCor() != null && a.getCor().equalsIgnoreCase(b.getCor())) {
            score += 20;
        }
        if (a.getLocal() != null && a.getLocal().equalsIgnoreCase(b.getLocal())) {
            score += 10;
        }

        return score;
    }

    /**
     * Busca todos os animais perdidos no banco e calcula o score
     * com o animal recém cadastrado. Salva as correspondências com score > 0.
     */
    public void buscarCorrespondencias(Animal animalNovo) {
        List<Animal> perdidos = animalRepository.findByStatus(StatusAnimal.PERDIDO);

        for (Animal perdido : perdidos) {
            double score = calcularScore(perdido, animalNovo);

            if (score > 0) {
                Correspondencia correspondencia = new Correspondencia();
                correspondencia.setAnimalPerdido(perdido);
                correspondencia.setAnimalEncontrado(animalNovo);
                correspondencia.setScoreSimilaridade(score);
                correspondencia.setStatus(StatusCorrespondencia.PENDENTE);
                correspondenciaRepository.save(correspondencia);
            }
        }
    }
}