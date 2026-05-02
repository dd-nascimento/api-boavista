package com.david.api_boavista.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.david.api_boavista.dto.UsuarioRequestDTO;
import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // 🔹 CREATE
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // depois vamos criptografar
        usuario.setRole("USER"); // ou enum se você tiver

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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return toResponseDTO(usuario);
    }

    // 🔹 ATUALIZAR
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Usuario atualizado = usuarioRepository.save(usuario);

        return toResponseDTO(atualizado);
    }

    // 🔹 DELETAR
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // 🔁 CONVERSOR (ESSENCIAL)
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}