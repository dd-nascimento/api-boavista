package com.david.api_boavista.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.david.api_boavista.enums.CategoriaPedido;
import com.david.api_boavista.enums.StatusPedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bv_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true, updatable = false, length = 19)
    private String numeroPedido;

    @Enumerated(EnumType.STRING)
    @Column (name = "categoria_pedido", nullable = false)
    private CategoriaPedido categoriaPedido;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "dataHora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "dataHora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column (name = "local_execucao", nullable = false)
    private String localExecucao;

    @Column (name = "titulo", nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_solicitante", nullable = false)
    private Usuario usuarioSolicitante;
    
    @ManyToOne
    @JoinColumn(name = "id_solucionador", nullable = true)
    private Usuario usuarioSolucionador;

    @Enumerated(EnumType.STRING)
    @Column (name = "status_pedido", nullable = false)
    private StatusPedido statusPedido = StatusPedido.ABERTO;
}
