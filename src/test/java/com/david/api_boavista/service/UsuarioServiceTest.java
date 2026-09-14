package com.david.api_boavista.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.repository.UsuarioRepository;

import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock 
    UsuarioRepository usuarioRepository;

    @Mock 
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UsuarioService usuarioService;
    
    @Test 
    void buscarPorId_deveRetornarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(01L);
        usuario.setNome("João Henrique Moura");
        usuario.setEmail("jhmoura@apiboavista.com");

        when(usuarioRepository.findById(01L))
        .thenReturn(Optional.of(usuario));

        UsuarioResponseDTO resultado =
        usuarioService.buscarPorId(01L);

        // Verifica se o resultado é igual ao esperado
        assertEquals(01L, resultado.getId());
        assertEquals("João Henrique Moura", resultado.getNome());
        assertEquals("jhmoura@apiboavista.com", resultado.getEmail());
    }
}
