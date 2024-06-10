package com.libreriaproyect.libreriproyecto.repository;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeccionRepositorio extends JpaRepository<Seccion, Long> {
    // Puedes añadir métodos de consulta personalizados aquí si es necesario
}