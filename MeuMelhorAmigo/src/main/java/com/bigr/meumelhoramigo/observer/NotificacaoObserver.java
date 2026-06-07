package com.bigr.meumelhoramigo.observer;

import com.bigr.meumelhoramigo.events.AnimalCadastradoEvent;
import com.bigr.meumelhoramigo.modelo.Animal;
import com.bigr.meumelhoramigo.modelo.StatusAnimal;
import com.bigr.meumelhoramigo.service.CorrespondenciaService;
import com.bigr.meumelhoramigo.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * GoF Observer — reage ao evento AnimalCadastradoEvent
 * sem acoplamento direto ao AnimalService
 */
@Component
public class NotificacaoObserver {

    @Autowired
    private CorrespondenciaService correspondenciaService;

    @Autowired
    private NotificacaoService notificacaoService;

    @EventListener
    public void onAnimalCadastrado(AnimalCadastradoEvent evento) {
        Animal animalNovo = evento.getAnimal();

        // Só notifica se for um animal encontrado
        if (animalNovo.getStatus() != StatusAnimal.ENCONTRADO) return;

        // Busca animais perdidos que tenham similaridade com o novo animal
        correspondenciaService.buscarCorrespondencias(animalNovo);

        // Notifica o responsável pelo animal encontrado
        if (animalNovo.getResponsavel() != null) {
            notificacaoService.criarNotificacao(
                    animalNovo.getResponsavel(),
                    "CORRESPONDENCIA",
                    "Seu animal cadastrado pode ter correspondências com animais perdidos!"
            );
        }
    }
}