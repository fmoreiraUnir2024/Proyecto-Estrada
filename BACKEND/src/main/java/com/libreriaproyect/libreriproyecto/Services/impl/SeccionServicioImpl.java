package com.libreriaproyect.libreriproyecto.Services.impl;
import com.libreriaproyect.libreriproyecto.Services.SeccionServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Seccion;
import com.libreriaproyect.libreriproyecto.repository.SeccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeccionServicioImpl implements SeccionServicio {

    @Autowired
    private SeccionRepositorio seccionRepositorio;

    @Override
    public Seccion crearSeccion(Seccion seccion) {
        return seccionRepositorio.save(seccion);
    }

    @Override
    public Seccion obtenerSeccionPorId(Long id) {
        Optional<Seccion> seccion = seccionRepositorio.findById(id);
        return seccion.orElse(null);
    }

    @Override
    public List<Seccion> obtenerTodasLasSecciones() {
        return seccionRepositorio.findAll();
    }

    @Override
    public Seccion actualizarSeccion(Long id, Seccion seccion) {
        if (seccionRepositorio.existsById(id)) {
            seccion.setId(id);
            return seccionRepositorio.save(seccion);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarSeccion(Long id) {
        seccionRepositorio.deleteById(id);
    }
}