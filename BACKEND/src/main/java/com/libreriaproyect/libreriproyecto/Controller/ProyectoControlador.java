package com.libreriaproyect.libreriproyecto.Controller;
import com.libreriaproyect.libreriproyecto.Services.ProyectoServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.ProyectoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProyectoControlador {

    @Autowired
    private ProyectoServicio proyectoServicio;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Proyecto>> obtenerProyectosPorUsuarioId(@PathVariable Long usuarioId) {
        List<Proyecto> proyectos = proyectoServicio.obtenerProyectosPorUsuarioId(usuarioId);
        return ResponseEntity.ok(proyectos);
    }
    @PostMapping
    public ResponseEntity<Proyecto> crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        Proyecto proyecto = proyectoServicio.crearProyecto(proyectoDTO);
        return new ResponseEntity<>(proyecto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Integer id) {
        Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(id);
        ProyectoDTO.builder().nombre(proyecto.getNombre())
                .descripcion(proyecto.getDescripcion())
                .plantilla_id(proyecto.getPlantilla().getId()).id( proyecto.getId()).build();
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = proyectoServicio.obtenerTodosLosProyectos();
        return ResponseEntity.ok(proyectos);
    }
    @PutMapping("contenido/{id}")
    public ResponseEntity<Proyecto> actualizarContenido(@PathVariable Integer id, @RequestBody String nuevoContenido) {
        Proyecto proyectoActualizado = proyectoServicio.actualizarContenido(id, nuevoContenido);
        if (proyectoActualizado != null) {
            return ResponseEntity.ok(proyectoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Integer id, @RequestBody Proyecto proyecto) {
        Proyecto proyectoActualizado = proyectoServicio.actualizarProyecto(id, proyecto);
        if (proyectoActualizado != null) {
            return ResponseEntity.ok(proyectoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Integer id) {
        proyectoServicio.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}