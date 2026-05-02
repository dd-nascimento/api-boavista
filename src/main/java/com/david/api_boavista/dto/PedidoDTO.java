package com.david.api_boavista.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

    private String descricao;
    private Double valor;
    private Long id_usuario;
    
}
