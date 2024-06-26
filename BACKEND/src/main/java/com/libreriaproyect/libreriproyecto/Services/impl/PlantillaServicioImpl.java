package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.Services.PlantillaServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.PlantillaDTO;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import com.libreriaproyect.libreriproyecto.repository.PlantillaRepositorio;
import com.libreriaproyect.libreriproyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantillaServicioImpl implements PlantillaServicio {
    @Autowired
    private UserRepository usuarioRepositorio;
    @Autowired
    private PlantillaRepositorio plantillaRepositorio;

    @Override
    public Plantilla crearPlantilla(PlantillaDTO plantillaDTO) {
        Usuario usuario = usuarioRepositorio.findById(plantillaDTO.getUsuario_id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Plantilla plantilla = new Plantilla();
        plantilla.setNombre(plantillaDTO.getNombre());
        plantilla.setFormato(plantillaDTO.getFormato());
        plantilla.setUsuario(usuario);

        return plantillaRepositorio.save(plantilla);
    }
    @Override
    public Plantilla obtenerPlantillaPorId(Long id) {
        Optional<Plantilla> plantilla = plantillaRepositorio.findById(id);
        return plantilla.orElse(null);
    }
    @Override
    public List<Plantilla> obtenerPlantillasPorUsuario(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        return plantillaRepositorio.findByUsuario(usuario);
    }
    @Override
    public List<Plantilla> obtenerTodasLasPlantillas() {
        return plantillaRepositorio.findAll();
    }

    @Override
    public Plantilla actualizarPlantilla(Long id, Plantilla plantilla) {
        if (plantillaRepositorio.existsById(id)) {
            plantilla.setId(id);
            return plantillaRepositorio.save(plantilla);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarPlantilla(Long id) {
        plantillaRepositorio.deleteById(id);
    }
    @Override
    public List<Plantilla> obtenerPlantillasPorUsuarioId(Long usuarioId) {
        return plantillaRepositorio.findByUsuarioId(usuarioId);
    }

}