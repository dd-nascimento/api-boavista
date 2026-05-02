package com.david.api_boavista.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.david.api_boavista.entities.Pedido;
import com.david.api_boavista.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Pedido atualizar(Long id, Pedido novoPedido) {

        Pedido pedido = buscarPorId(id);

        pedido.setDescricao(novoPedido.getDescricao());
        pedido.setValor(novoPedido.getValor());
        pedido.setPago(novoPedido.getPago());

        return pedidoRepository.save(pedido);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido pagar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedido.setPago(true);
        return pedidoRepository.save(pedido);
    }
}