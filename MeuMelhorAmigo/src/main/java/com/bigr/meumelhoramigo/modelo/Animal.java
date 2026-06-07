package com.bigr.meumelhoramigo.modelo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidade base de Animal. Usa heranca JOINED (tabelas separadas para
 * AnimalEncontrado e AnimalPerdido), conforme orientacao do professor.
 */
@Entity
@Table(name = "animais")
@Inheritance(strategy = InheritanceType.JOINED)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String especie;

    private String raca;

    private String cor;

    private String tamanho;

    private String sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status;

    private String local;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @Column(length = 2000)
    private String descricao;

    // Caminho/URL da foto salva na pasta de uploads
    private String foto;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public StatusAnimal getStatus() {
        return status;
    }

    public void setStatus(StatusAnimal status) {
        this.status = status;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }
}
