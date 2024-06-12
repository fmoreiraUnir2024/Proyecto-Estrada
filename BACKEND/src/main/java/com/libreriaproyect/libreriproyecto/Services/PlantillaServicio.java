package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.PlantillaDTO;

import java.util.List;

public interface PlantillaServicio {
    Plantilla crearPlantilla(PlantillaDTO plantillaDTO);
    List<Plantilla> obtenerPlantillasPorUsuarioId(Long usuarioId);
    List<Plantilla> obtenerPlantillasPorUsuario(String username);
    Plantilla obtenerPlantillaPorId(Long id);
    List<Plantilla> obtenerTodasLasPlantillas();
    Plantilla actualizarPlantilla(Long id, Plantilla plantilla);
    void eliminarPlantilla(Long id);
}