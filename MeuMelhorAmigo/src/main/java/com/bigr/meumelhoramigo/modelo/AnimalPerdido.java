package com.bigr.meumelhoramigo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * Animal perdido cadastrado por um tutor/dono (RF5). Herda de Animal e
 * adiciona dados especificos do desaparecimento.
 */
@Entity
@Table(name = "animais_perdidos")
public class AnimalPerdido extends Animal {

    private String nome;

    private LocalDate dataDesaparecimento;

    @Column(length = 2000)
    private String caracteristicas;

    private String recompensa;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDesaparecimento() {
        return dataDesaparecimento;
    }

    public void setDataDesaparecimento(LocalDate dataDesaparecimento) {
        this.dataDesaparecimento = dataDesaparecimento;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(String recompensa) {
        this.recompensa = recompensa;
    }
}
