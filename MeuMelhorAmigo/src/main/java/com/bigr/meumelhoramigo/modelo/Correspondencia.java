package com.bigr.meumelhoramigo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.bigr.meumelhoramigo.events.*;
@Entity
@Table(name = "correspondencias")
public class Correspondencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "animal_perdido_id")
    private Animal animalPerdido;

    @ManyToOne
    @JoinColumn(name = "animal_encontrado_id")
    private Animal animalEncontrado;

    private Double scoreSimilaridade;

    private LocalDateTime dataSugestao;

    @Enumerated(EnumType.STRING)
    private StatusCorrespondencia status = StatusCorrespondencia.PENDENTE;

    @PrePersist
    public void prePersist() {
        if (dataSugestao == null) {
            dataSugestao = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Animal getAnimalPerdido() { return animalPerdido; }
    public void setAnimalPerdido(Animal animalPerdido) { this.animalPerdido = animalPerdido; }

    public Animal getAnimalEncontrado() { return animalEncontrado; }
    public void setAnimalEncontrado(Animal animalEncontrado) { this.animalEncontrado = animalEncontrado; }

    public Double getScoreSimilaridade() { return scoreSimilaridade; }
    public void setScoreSimilaridade(Double scoreSimilaridade) { this.scoreSimilaridade = scoreSimilaridade; }

    public LocalDateTime getDataSugestao() { return dataSugestao; }
    public void setDataSugestao(LocalDateTime dataSugestao) { this.dataSugestao = dataSugestao; }

    public StatusCorrespondencia getStatus() { return status; }
    public void setStatus(StatusCorrespondencia status) { this.status = status; }
}