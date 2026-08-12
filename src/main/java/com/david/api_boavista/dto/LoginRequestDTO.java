package com.david.api_boavista.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O campo email deve ser um email válido")
    private String email;

    @NotBlank(message = "O campo senha é obrigatório")
    private String password;

}
