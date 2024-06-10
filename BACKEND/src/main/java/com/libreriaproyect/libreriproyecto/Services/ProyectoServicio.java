package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;

import java.util.List;

public interface ProyectoServicio {
    Proyecto crearProyecto(Proyecto proyecto);
    Proyecto obtenerProyectoPorId(Long id);
    List<Proyecto> obtenerTodosLosProyectos();
    Proyecto actualizarProyecto(Long id, Proyecto proyecto);
    void eliminarProyecto(Long id);
    List<Proyecto> obtenerProyectosPorUsuarioId(Long usuarioId);

}