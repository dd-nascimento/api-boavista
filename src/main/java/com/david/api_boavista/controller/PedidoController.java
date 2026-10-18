package com.david.api_boavista.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.api_boavista.service.PedidoService;

import jakarta.validation.Valid;

import com.david.api_boavista.dto.PedidoResponseDTO;
import com.david.api_boavista.dto.PedidoDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping 
    public PedidoResponseDTO criar (@Valid @RequestBody PedidoDTO dto){
        return pedidoService.criar(dto);
    }

    @GetMapping
    public List<PedidoResponseDTO> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public PedidoResponseDTO buscar(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }
}