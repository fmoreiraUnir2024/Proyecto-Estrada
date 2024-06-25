package com.libreriaproyect.libreriproyecto.Services.impl;
import com.libreriaproyect.libreriproyecto.Services.ProyectoServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.ProyectoDTO;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import com.libreriaproyect.libreriproyecto.repository.PlantillaRepositorio;
import com.libreriaproyect.libreriproyecto.repository.ProyectoRepositorio;
import com.libreriaproyect.libreriproyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServicioImpl implements ProyectoServicio {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;
    @Autowired
    private UserRepository usuarioRepositorio;

    @Autowired
    private PlantillaRepositorio plantillaRepositorio;
    @Override
    public Proyecto crearProyecto(ProyectoDTO proyectoDTO) {
        Usuario usuario = usuarioRepositorio.findById(proyectoDTO.getUsuario_id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Plantilla plantilla = plantillaRepositorio
                .findById(proyectoDTO.getPlantilla_id())
                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setTipoArticulo(proyectoDTO.getTipo_articulo());
        proyecto.setUsuario(usuario);
        proyecto.setPlantilla(plantilla);

        return proyectoRepositorio.save(proyecto);
    }

    @Override
    public Proyecto obtenerProyectoPorId(Integer id) {
        Optional<Proyecto> proyecto = proyectoRepositorio.findById(id);
        return proyecto.orElse(null);
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepositorio.findAll();
    }

    @Override
    public Proyecto actualizarProyecto(ProyectoDTO proyectoDTO) {

        if (proyectoRepositorio.existsById(proyectoDTO.getId())) {

            Plantilla plantilla = plantillaRepositorio.findById(proyectoDTO.getPlantilla_id())
                    .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));
            Usuario usuario = usuarioRepositorio.findById(proyectoDTO.getUsuario_id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            return proyectoRepositorio.save(Proyecto.builder()
                    .nombre(proyectoDTO.getNombre())
                    .descripcion(proyectoDTO.getDescripcion())
                    .tipoArticulo(proyectoDTO.getTipo_articulo())
                    .plantilla(plantilla)
                    .usuario(usuario)
                    .build());

        } else {
            return null;
        }
    }
    @Override
    public List<Proyecto> obtenerProyectosPorUsuarioId(Long usuarioId) {
        return proyectoRepositorio.findByUsuarioId(usuarioId);
    }

    @Override
    public Proyecto actualizarContenido(Integer id, String nuevoContenido) {
        Optional<Proyecto> proyectoOpt = proyectoRepositorio.findById(id);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.setContenido(nuevoContenido);
            return proyectoRepositorio.save(proyecto);
        }
        return null;
    }


    @Override
    public void eliminarProyecto(Integer id) {
        proyectoRepositorio.deleteById(id);
    }
}