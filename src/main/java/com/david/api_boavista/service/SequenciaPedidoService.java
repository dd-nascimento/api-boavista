package com.david.api_boavista.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.david.api_boavista.repository.SequenciaPedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class SequenciaPedidoService {
    
    private final SequenciaPedidoRepository sequenciaPedidoRepository;

    @Transactional
    public Long obterProximoNumero(LocalDate data) {

        sequenciaPedidoRepository.incrementarOuCriar(data);

        return sequenciaPedidoRepository.findById(data)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Não foi possível obter a sequência do pedido"))
                .getUltimoNumero();
    }
}
