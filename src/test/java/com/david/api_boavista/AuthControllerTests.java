package com.david.api_boavista;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.testcontainers.mysql.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import com.david.api_boavista.entities.Usuario;
import com.david.api_boavista.enums.Role;
import com.david.api_boavista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers 
@SpringBootTest 
class AuthControllerTests {

    @Container 
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0.33");

    @DynamicPropertySource
    static void configurarBanco(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired 
    UsuarioRepository usuarioRepository;

    @Test
    void deveSalvarUsuarioNoBancoDeTeste() {

    Usuario usuario = new Usuario();

    usuario.setNome("Ricardo Brandão Mattos");
    usuario.setEmail("ricardo.mattos@apiboavista.com");
    usuario.setSenha("ricardinho123");
    usuario.setRole(Role.USER);

    Usuario salvo = usuarioRepository.save(usuario);

    assertNotNull(salvo.getId());
    }

    @Test
    void deveEncontrarUsuarioSalv(){

        Usuario usuario = new Usuario();

        usuario.setNome("Silvia Carolina Ribeiro");
        usuario.setEmail("silvia.ribeiro@apiboavista.com");
        usuario.setSenha("silvinha123");
        usuario.setRole(Role.USER);

        Usuario salvo = usuarioRepository.save(usuario);
        assertNotNull(salvo.getId());

        Optional<Usuario> encontrado = usuarioRepository.findById(salvo.getId());
        assertNotNull(encontrado.isPresent());
        assertEquals("Silvia Carolina Ribeiro", encontrado.get().getNome());

    }
}