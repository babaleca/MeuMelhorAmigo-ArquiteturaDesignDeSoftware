package com.bigr.meumelhoramigo.events;

import com.bigr.meumelhoramigo.modelo.Animal;
import org.springframework.context.ApplicationEvent;

public class AnimalCadastradoEvent extends ApplicationEvent {

    private final Animal animal;

    public AnimalCadastradoEvent(Object source, Animal animal) {
        super(source);
        this.animal = animal;
    }

    public Animal getAnimal() {
        return animal;
    }
}
