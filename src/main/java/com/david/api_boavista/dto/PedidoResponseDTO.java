package com.david.api_boavista.dto;

import java.time.LocalDateTime;

import com.david.api_boavista.enums.CategoriaPedido;
import com.david.api_boavista.enums.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class PedidoResponseDTO {
    
    private Long id;
    private String numeroPedido;
    private CategoriaPedido categoriaPedido;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String localExecucao;
    private String titulo;
    private String descricao;
    private StatusPedido statusPedido;
    private String solicitanteNome;
    private String solicitanteEmail;
    private String solucionadorNome;
    private String solucionadorEmail;

}
