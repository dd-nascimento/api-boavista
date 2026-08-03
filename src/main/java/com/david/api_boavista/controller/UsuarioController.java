package com.david.api_boavista.controller;

import org.springframework.web.bind.annotation.*;

import com.david.api_boavista.dto.UsuarioRequestDTO;
import com.david.api_boavista.dto.UsuarioResponseDTO;
import com.david.api_boavista.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/listUser")
    public java.util.List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    // 🔹 CRIAR USUÁRIO
    @PostMapping("/nwUser")
    public UsuarioResponseDTO cadastrarUsuario
        (@RequestBody @Valid UsuarioRequestDTO dto) {
            return usuarioService.salvar(dto);
    }

    // 🔹 DELETAR
    @DeleteMapping("/dell/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
    }
}