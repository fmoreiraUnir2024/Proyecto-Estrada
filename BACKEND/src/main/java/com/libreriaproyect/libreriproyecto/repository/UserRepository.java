package com.libreriaproyect.libreriproyecto.repository;

import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByUsername(String username);


}
