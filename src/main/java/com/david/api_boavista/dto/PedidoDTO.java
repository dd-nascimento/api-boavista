package com.david.api_boavista.dto;

import java.time.LocalDateTime;

import com.david.api_boavista.enums.CategoriaPedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

    @NotNull
    private CategoriaPedido categoriaPedido;

    @NotNull
    private LocalDateTime dataHoraInicio;

    @NotNull
    private LocalDateTime dataHoraFim;

    @NotBlank
    private String localExecucao;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;
    
}
