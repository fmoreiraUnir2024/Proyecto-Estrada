package com.libreriaproyect.libreriproyecto.repository;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantillaRepositorio extends JpaRepository<Plantilla, Long> {
    List<Plantilla> findByUsuarioId(Long usuarioId);

}