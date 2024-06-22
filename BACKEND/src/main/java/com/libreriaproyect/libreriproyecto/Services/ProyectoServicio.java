package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.ProyectoDTO;

import java.util.List;

public interface ProyectoServicio {
    Proyecto crearProyecto(ProyectoDTO proyecto);
    Proyecto obtenerProyectoPorId(Integer id);
    List<Proyecto> obtenerTodosLosProyectos();
    Proyecto actualizarProyecto(Integer id, Proyecto proyecto);
    void eliminarProyecto(Integer id);
    List<Proyecto> obtenerProyectosPorUsuarioId(Long usuarioId);
    Proyecto actualizarContenido(Integer id, String nuevoContenido);

}