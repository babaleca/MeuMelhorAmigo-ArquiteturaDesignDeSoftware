package com.bigr.meumelhoramigo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Animal encontrado/resgatado (RF4). Herda de Animal e adiciona dados
 * especificos do encontro.
 */
@Entity
@Table(name = "animais_encontrados")
public class AnimalEncontrado extends Animal {

    private LocalDate dataEncontro;

    private String condicaoSaude;

    private String localEncontro;

    public LocalDate getDataEncontro() {
        return dataEncontro;
    }

    public void setDataEncontro(LocalDate dataEncontro) {
        this.dataEncontro = dataEncontro;
    }

    public String getCondicaoSaude() {
        return condicaoSaude;
    }

    public void setCondicaoSaude(String condicaoSaude) {
        this.condicaoSaude = condicaoSaude;
    }

    public String getLocalEncontro() {
        return localEncontro;
    }

    public void setLocalEncontro(String localEncontro) {
        this.localEncontro = localEncontro;
    }
}
