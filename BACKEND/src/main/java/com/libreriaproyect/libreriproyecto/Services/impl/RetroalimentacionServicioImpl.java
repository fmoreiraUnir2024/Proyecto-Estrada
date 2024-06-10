package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.Services.RetroalimentacionServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Retroalimentacion;
import com.libreriaproyect.libreriproyecto.repository.RetroalimentacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetroalimentacionServicioImpl implements RetroalimentacionServicio {

    @Autowired
    private RetroalimentacionRepositorio retroalimentacionRepositorio;

    @Override
    public Retroalimentacion crearRetroalimentacion(Retroalimentacion retroalimentacion) {
        return retroalimentacionRepositorio.save(retroalimentacion);
    }

    @Override
    public Retroalimentacion obtenerRetroalimentacionPorId(Long id) {
        Optional<Retroalimentacion> retroalimentacion = retroalimentacionRepositorio.findById(id);
        return retroalimentacion.orElse(null);
    }

    @Override
    public List<Retroalimentacion> obtenerTodasLasRetroalimentaciones() {
        return retroalimentacionRepositorio.findAll();
    }

    @Override
    public Retroalimentacion actualizarRetroalimentacion(Long id, Retroalimentacion retroalimentacion) {
        if (retroalimentacionRepositorio.existsById(id)) {
            retroalimentacion.setId(id);
            return retroalimentacionRepositorio.save(retroalimentacion);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarRetroalimentacion(Long id) {
        retroalimentacionRepositorio.deleteById(id);
    }
}