package com.tribus.cadastro_usuario.infrastructure.repository;
import com.tribus.cadastro_usuario.infrastructure.entitys.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    @Transactional //caso de algum erro, essa anotação fara com que não delete
    void deleteByEmail(String email);

}
