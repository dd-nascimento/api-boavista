package com.david.api_boavista.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.api_boavista.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    
}
