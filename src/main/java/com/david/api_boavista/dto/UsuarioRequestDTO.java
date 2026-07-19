package com.david.api_boavista.dto;
import com.david.api_boavista.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private Role role;
}