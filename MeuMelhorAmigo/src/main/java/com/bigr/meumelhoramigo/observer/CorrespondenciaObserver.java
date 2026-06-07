package com.bigr.meumelhoramigo.observer;

import com.bigr.meumelhoramigo.events.AnimalCadastradoEvent;
import com.bigr.meumelhoramigo.service.CorrespondenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * GoF Observer — reage ao evento AnimalCadastradoEvent
 * sem acoplamento direto ao AnimalService
 */
@Component
public class CorrespondenciaObserver {

    @Autowired
    private CorrespondenciaService correspondenciaService;

    @EventListener
    public void onAnimalCadastrado(AnimalCadastradoEvent evento) {
        correspondenciaService.buscarCorrespondencias(evento.getAnimal());
    }
}