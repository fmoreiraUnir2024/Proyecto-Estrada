package com.libreriaproyect.libreriproyecto.repository;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByUsuarioId(Long usuarioId);

}