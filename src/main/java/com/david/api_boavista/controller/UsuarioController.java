package com.david.api_boavista.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.david.api_boavista.dto.RoleUpdateDTO;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public java.util.List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    // 🔹 BUSCAR POR ID
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    // 🔹 CRIAR USUÁRIO
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/nwUser")
    public UsuarioResponseDTO cadastrarUsuario
        (@RequestBody @Valid UsuarioRequestDTO dto) {
            return usuarioService.salvar(dto);
    }

    // 🔹 DELETAR
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/dell/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

    // 🔹 ATUALIZAR ROLE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/role")
    public UsuarioResponseDTO alterarRole(@PathVariable Long id, @RequestBody @Valid RoleUpdateDTO dto) {
        return usuarioService.alterarRole(id, dto);
    }

    // 🔹 ATUALIZAR CADASTRO
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto) {
        return usuarioService.atualizar(id, dto);
    }
}