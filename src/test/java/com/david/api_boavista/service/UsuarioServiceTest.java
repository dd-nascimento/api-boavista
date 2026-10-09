package com.david.api_boavista.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.david.api_boavista.dto.RoleUpdateDTO;
import com.david.api_boavista.dto.UsuarioRequestDTO;
import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.enums.Role;
import com.david.api_boavista.exception.EmailJaCadastradoException;
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

    @Test
    void salvar_deveLancarExcecaoQuandoEmailJaExiste() {
        
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNome("João Henrique Moura");
        usuarioRequestDTO.setEmail("jhmoura@apiboavista.com");
        usuarioRequestDTO.setSenha("123456");

        when(usuarioRepository.existsByEmail(usuarioRequestDTO.getEmail()))
            .thenReturn(true);

        assertThrows(EmailJaCadastradoException.class,
            () -> usuarioService.salvar(usuarioRequestDTO)
        );

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void listarTodos_deveRetornarListaDeUsuarios() {

        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNome("João Henrique Moura");
        usuario1.setEmail("jhmoura@apiboavista.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria Silva");
        usuario2.setEmail("msmaria@apiboavista.com");

        when(usuarioRepository.findAll())
            .thenReturn(List.of(usuario1, usuario2));
        
        List<UsuarioResponseDTO> resultado = usuarioService.listarTodos();

        assertEquals(2, resultado.size());

        assertEquals(1L, resultado.get(0).getId());
        assertEquals("João Henrique Moura", resultado.get(0).getNome());
        assertEquals("jhmoura@apiboavista.com", resultado.get(0).getEmail());

        assertEquals(2L, resultado.get(1).getId());
        assertEquals("Maria Silva", resultado.get(1).getNome());
        assertEquals("msmaria@apiboavista.com", resultado.get(1).getEmail());
    }

    @Test 
    void listarTodos_deveRetornarListaVaziaQuandoNaoExistiremUsuarios() {

        when(usuarioRepository.findAll())
            .thenReturn(List.of());
        
        List<UsuarioResponseDTO> resultado = usuarioService.listarTodos();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void atualizar_deveRetornarUsuarioAtualizado(){

        Usuario usuario = new Usuario();
            usuario.setId(1L);
            usuario.setNome("João Henrique Moura");
            usuario.setEmail("jhmoura@apiboavista.com");

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
            usuarioRequestDTO.setNome("João Henrique");
            usuarioRequestDTO.setEmail("joao@apiboavista.com");
            usuarioRequestDTO.setSenha("654321");

        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

        when(passwordEncoder.encode(usuarioRequestDTO.getSenha()))
            .thenReturn("senhaCriptografada");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(1L);
        usuarioAtualizado.setNome("João Henrique");
        usuarioAtualizado.setEmail("joao@apiboavista.com");
        usuarioAtualizado.setSenha("senhaCriptografada");

        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuarioAtualizado);

        // Act
        UsuarioResponseDTO resultado =
            usuarioService.atualizar(1L, usuarioRequestDTO);

        // Assert - retorno
        assertEquals(1L, resultado.getId());
        assertEquals("João Henrique", resultado.getNome());
        assertEquals("joao@apiboavista.com", resultado.getEmail());

        // Assert - entidade enviada ao Repository
        ArgumentCaptor<Usuario> usuarioCaptor =
            ArgumentCaptor.forClass(Usuario.class);

        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioCapturado = usuarioCaptor.getValue();

        assertEquals("João Henrique", usuarioCapturado.getNome());
        assertEquals("joao@apiboavista.com", usuarioCapturado.getEmail());
        assertEquals("senhaCriptografada", usuarioCapturado.getSenha());
        verify(passwordEncoder).encode(usuarioRequestDTO.getSenha());
    }

    @Test 
    void atualizar_deveLancarExcecaoQuandoUsuarioNaoExiste(){

        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.empty());
        
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        
        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.atualizar(1L, usuarioRequestDTO);
        });

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test 
    void alterarRole_deveRetornarUsuarioAtualizado() {
        
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Henrique de Moura");
        usuario.setRole(Role.USER);
        
        RoleUpdateDTO roleUpdateDTO = new RoleUpdateDTO();
        roleUpdateDTO.setRole(Role.ADMIN);
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioResponseDTO resultado =
            usuarioService.alterarRole(1L, roleUpdateDTO);

        assertEquals(1L, resultado.getId());
        assertEquals("João Henrique de Moura", resultado.getNome());
        assertEquals(Role.ADMIN, resultado.getRole());

        ArgumentCaptor<Usuario> usuarioCaptor =
            ArgumentCaptor.forClass(Usuario.class);
        
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioCapturado = usuarioCaptor.getValue();
        assertEquals(Role.ADMIN, usuarioCapturado.getRole());
    }

    @Test
    void alterarRole_deveLancarExcecaoQuandoUsuarioNaoExiste() {
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.empty());

        RoleUpdateDTO roleUpdateDTO = new RoleUpdateDTO();
            roleUpdateDTO.setRole(Role.ADMIN);
        
        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.alterarRole(1L, roleUpdateDTO);
        });

        verify(usuarioRepository, never()).save(any(Usuario.class));
        
    }
}
