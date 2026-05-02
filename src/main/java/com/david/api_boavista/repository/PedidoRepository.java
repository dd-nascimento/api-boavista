package com.david.api_boavista.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.api_boavista.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findById(Long id);
    
}
