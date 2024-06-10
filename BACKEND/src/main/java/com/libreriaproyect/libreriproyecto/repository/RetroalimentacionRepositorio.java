package com.libreriaproyect.libreriproyecto.repository;

import com.libreriaproyect.libreriproyecto.entidades.documento.Retroalimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetroalimentacionRepositorio extends JpaRepository<Retroalimentacion, Long> {
    // Puedes añadir métodos de consulta personalizados aquí si es necesario
}