package com.libreriaproyect.libreriproyecto.Services.impl;
import com.libreriaproyect.libreriproyecto.Services.ProyectoServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import com.libreriaproyect.libreriproyecto.repository.ProyectoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServicioImpl implements ProyectoServicio {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Override
    public Proyecto crearProyecto(Proyecto proyecto) {
        return proyectoRepositorio.save(proyecto);
    }

    @Override
    public Proyecto obtenerProyectoPorId(Long id) {
        Optional<Proyecto> proyecto = proyectoRepositorio.findById(id);
        return proyecto.orElse(null);
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepositorio.findAll();
    }

    @Override
    public Proyecto actualizarProyecto(Long id, Proyecto proyecto) {
        if (proyectoRepositorio.existsById(id)) {
            proyecto.setId(id);
            return proyectoRepositorio.save(proyecto);
        } else {
            return null;
        }
    }
    @Override
    public List<Proyecto> obtenerProyectosPorUsuarioId(Long usuarioId) {
        return proyectoRepositorio.findByUsuarioId(usuarioId);
    }


    @Override
    public void eliminarProyecto(Long id) {
        proyectoRepositorio.deleteById(id);
    }
}