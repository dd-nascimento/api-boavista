package com.david.api_boavista.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bv_sequencia_pedido")
public class SequenciaPedido {
    
    @Id
    @Column(name = "data_sequencia", nullable = false)
    private LocalDate dataSequencia;

    @Column(name = "ultimo_numero", nullable = false)
    private Long ultimoNumero;
}
