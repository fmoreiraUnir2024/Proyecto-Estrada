package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;

import java.util.List;

public interface PlantillaServicio {
    Plantilla crearPlantilla(Plantilla plantilla, Long usuarioId);
    List<Plantilla> obtenerPlantillasPorUsuarioId(Long usuarioId);
    List<Plantilla> obtenerPlantillasPorUsuario(String username);
    Plantilla obtenerPlantillaPorId(Long id);
    List<Plantilla> obtenerTodasLasPlantillas();
    Plantilla actualizarPlantilla(Long id, Plantilla plantilla);
    void eliminarPlantilla(Long id);
}