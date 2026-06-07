package com.bigr.meumelhoramigo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_destino_id")
    private Usuario usuarioDestino;

    private String tipo;

    @Column(length = 2000)
    private String mensagem;

    private LocalDateTime dataEnvio;

    private boolean lida = false;

    @PrePersist
    public void prePersist() {
        if (dataEnvio == null) {
            dataEnvio = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuarioDestino() { return usuarioDestino; }
    public void setUsuarioDestino(Usuario usuarioDestino) { this.usuarioDestino = usuarioDestino; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }

    public boolean isLida() { return lida; }
    public void setLida(boolean lida) { this.lida = lida; }
}