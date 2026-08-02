package com.david.api_boavista.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.david.api_boavista.dto.UsuarioRequestDTO;
import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.repository.UsuarioRepository;

import com.david.api_boavista.exception.UsuarioNaoEncontradoException;

import com.david.api_boavista.enums.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔹 CREATE
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografar a senha
        usuario.setRole(dto.getRole());

        Usuario salvo = usuarioRepository.save(usuario);

        return toResponseDTO(salvo);
    }

    // 🔹 LISTAR TODOS
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔹 BUSCAR POR ID
    public UsuarioResponseDTO buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException
                    ("Usuário não encontrado"));

        return toResponseDTO(usuario);
    }

    // 🔹 ATUALIZAR
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException
                    ("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario atualizado = usuarioRepository.save(usuario);

        return toResponseDTO(atualizado);
    }

    // 🔹 DELETAR
    public void deletar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                new UsuarioNaoEncontradoException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }

    // 🔁 CONVERSOR (ESSENCIAL)
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .role(usuario.getRole())
                .build();
    }
}