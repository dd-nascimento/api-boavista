package com.david.api_boavista.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado e/ou não existe!"));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
