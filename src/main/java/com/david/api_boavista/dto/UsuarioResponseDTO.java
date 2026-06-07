package com.david.api_boavista.dto;

import com.david.api_boavista.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private Role role;
    
}
