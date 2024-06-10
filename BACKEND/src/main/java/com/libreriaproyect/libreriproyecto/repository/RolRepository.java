package com.libreriaproyect.libreriproyecto.repository;

import com.libreriaproyect.libreriproyecto.entidades.usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository  extends JpaRepository<Rol, Long> {
    public Rol findByNombre(String nombre);
}
