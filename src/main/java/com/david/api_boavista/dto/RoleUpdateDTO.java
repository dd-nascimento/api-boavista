package com.david.api_boavista.dto;

import com.david.api_boavista.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUpdateDTO {
    
    @NotNull
    private Role role;
}
