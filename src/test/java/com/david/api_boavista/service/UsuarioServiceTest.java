package com.david.api_boavista.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.david.api_boavista.dto.UsuarioRequestDTO;
import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.exception.UsuarioNaoEncontradoException;
import com.david.api_boavista.repository.UsuarioRepository;


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

    @Test
    void buscarPorId_deveLancarExcecaoQuandoUsuarioNaoExiste() {

        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.buscarPorId(1L);
        });
    }

    @Test
    void salvar_deveRetornarUsuarioQuandoEmailNaoExiste() {

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNome("João Henrique Moura");
        usuarioRequestDTO.setEmail("jhmoura@apiboavista.com");
        usuarioRequestDTO.setSenha("123456");

        when(usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail()))
            .thenReturn(false);
        
        when(passwordEncoder.encode(usuarioRequestDTO.getSenha()))
            .thenReturn("senhaCriptografada");
        
        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome("João Henrique Moura");
        usuarioSalvo.setEmail("jhmoura@apiboavista.com");

        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuarioSalvo);
        
        UsuarioResponseDTO resultado =
            usuarioService.salvar(usuarioRequestDTO);
        
        assertEquals(1L, resultado.getId());
        assertEquals("João Henrique Moura", resultado.getNome());
        assertEquals("jhmoura@apiboavista.com", resultado.getEmail());
        verify(passwordEncoder).encode(usuarioRequestDTO.getSenha());

        ArgumentCaptor<Usuario> usuarioCaptor =
            ArgumentCaptor.forClass(Usuario.class);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioSalvoCapturado = usuarioCaptor.getValue();
        
        assertEquals("senhaCriptografada", usuarioSalvoCapturado.getSenha());
        assertEquals("João Henrique Moura", usuarioSalvoCapturado.getNome());
        assertEquals("jhmoura@apiboavista.com", usuarioSalvoCapturado.getEmail());
    }
}
