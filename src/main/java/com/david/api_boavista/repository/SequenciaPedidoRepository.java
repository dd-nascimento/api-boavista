package com.david.api_boavista.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.david.api_boavista.entities.SequenciaPedido;

public interface SequenciaPedidoRepository extends JpaRepository<SequenciaPedido, LocalDate> {
    
    @Modifying
    @Query(value = """
            INSERT INTO bv_sequencia_pedido (data_sequencia, ultimo_numero)
            VALUES (:data, 1)
            ON DUPLICATE KEY UPDATE ultimo_numero = ultimo_numero + 1
            """, nativeQuery = true)
    void incrementarOuCriar(@Param("data") LocalDate data);
}
