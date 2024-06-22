package com.libreriaproyect.libreriproyecto.repository;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Integer> {

    List<Proyecto> findByUsuarioId(Long usuarioId);

}