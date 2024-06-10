package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.proyecto.Seccion;

import java.util.List;

public interface SeccionServicio {
    Seccion crearSeccion(Seccion seccion);
    Seccion obtenerSeccionPorId(Long id);
    List<Seccion> obtenerTodasLasSecciones();
    Seccion actualizarSeccion(Long id, Seccion seccion);
    void eliminarSeccion(Long id);
}
