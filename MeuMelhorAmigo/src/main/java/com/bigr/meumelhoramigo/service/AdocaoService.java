package com.bigr.meumelhoramigo.service;

import com.bigr.meumelhoramigo.modelo.*;
import com.bigr.meumelhoramigo.repository.AnimalRepository;
import com.bigr.meumelhoramigo.repository.SolicitacaoAdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdocaoService {

    @Autowired
    private SolicitacaoAdocaoRepository solicitacaoRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public SolicitacaoAdocao solicitar(Long animalId, Usuario adotante, String motivacao) {
        boolean jaExiste = solicitacaoRepository.existsByAnimalIdAndAdotanteIdAndStatus(
                animalId, adotante.getId(), StatusAdocao.PENDENTE);

        if (jaExiste) {
            throw new IllegalStateException("Você já tem uma solicitação pendente para este animal.");
        }

        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado."));

        SolicitacaoAdocao solicitacao = new SolicitacaoAdocao();
        solicitacao.setAnimal(animal);
        solicitacao.setAdotante(adotante);
        solicitacao.setMotivacao(motivacao);
        solicitacao.setStatus(StatusAdocao.PENDENTE);

        return solicitacaoRepository.save(solicitacao);
    }

    public void aprovar(Long solicitacaoId) {
        SolicitacaoAdocao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada."));

        solicitacao.setStatus(StatusAdocao.APROVADO);
        solicitacaoRepository.save(solicitacao);

        Animal animal = solicitacao.getAnimal();
        animal.setStatus(StatusAnimal.ADOTADO);
        animalRepository.save(animal);

        List<SolicitacaoAdocao> pendentes = solicitacaoRepository
                .findByAnimalIdAndStatus(animal.getId(), StatusAdocao.PENDENTE);
        for (SolicitacaoAdocao pendente : pendentes) {
            pendente.setStatus(StatusAdocao.RECUSADO);
        }
        solicitacaoRepository.saveAll(pendentes);
    }

    public void recusar(Long solicitacaoId) {
        SolicitacaoAdocao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada."));

        solicitacao.setStatus(StatusAdocao.RECUSADO);
        solicitacaoRepository.save(solicitacao);
    }

    public List<SolicitacaoAdocao> listarPorAnimal(Long animalId) {
        return solicitacaoRepository.findByAnimalId(animalId);
    }

    public List<SolicitacaoAdocao> listarPorAdotante(Long adotanteId) {
        return solicitacaoRepository.findByAdotanteId(adotanteId);
    }
}