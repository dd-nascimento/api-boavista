package com.david.api_boavista.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
}