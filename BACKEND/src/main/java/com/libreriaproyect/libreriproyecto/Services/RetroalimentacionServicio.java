package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.documento.Retroalimentacion;

import java.util.List;
public interface RetroalimentacionServicio {
    Retroalimentacion crearRetroalimentacion(Retroalimentacion retroalimentacion);
    Retroalimentacion obtenerRetroalimentacionPorId(Long id);
    List<Retroalimentacion> obtenerTodasLasRetroalimentaciones();
    Retroalimentacion actualizarRetroalimentacion(Long id, Retroalimentacion retroalimentacion);
    void eliminarRetroalimentacion(Long id);
}