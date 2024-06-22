package com.libreriaproyect.libreriproyecto.Controller;
import com.libreriaproyect.libreriproyecto.Services.PlantillaServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.dto.PlantillaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/plantillas")
public class PlantillaControlador {

    @Autowired
    private PlantillaServicio plantillaServicio;

  /*  @PostMapping
    public ResponseEntity<Plantilla> crearPlantilla(@RequestBody Plantilla plantilla, @RequestParam Long usuarioId) {
        Plantilla nuevaPlantilla = plantillaServicio.crearPlantilla(plantilla, usuarioId);
        return ResponseEntity.ok(nuevaPlantilla);
    }*/
    @PostMapping
    public ResponseEntity<Plantilla> crearPlantilla(@RequestBody PlantillaDTO plantillaDTO) {
        Plantilla plantilla = plantillaServicio.crearPlantilla(plantillaDTO);
        return new ResponseEntity<>(plantilla, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Plantilla> obtenerPlantillaPorId(@PathVariable Long id) {
        Plantilla plantilla = plantillaServicio.obtenerPlantillaPorId(id);
        if (plantilla != null) {
            return ResponseEntity.ok(plantilla);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/usuario")
    public List<Plantilla> obtenerPlantillasPorUsuario(Principal principal) {
        String username = principal.getName();
        return plantillaServicio.obtenerPlantillasPorUsuario(username);
    }
    @GetMapping
    public ResponseEntity<List<Plantilla>> obtenerTodasLasPlantillas() {
        List<Plantilla> plantillas = plantillaServicio.obtenerTodasLasPlantillas();
        return ResponseEntity.ok(plantillas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plantilla> actualizarPlantilla(@PathVariable Long id, @RequestBody Plantilla plantilla) {
        Plantilla plantillaActualizada = plantillaServicio.actualizarPlantilla(id, plantilla);
        if (plantillaActualizada != null) {
            return ResponseEntity.ok(plantillaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlantilla(@PathVariable Long id) {
        plantillaServicio.eliminarPlantilla(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Plantilla>> obtenerPlantillasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Plantilla> plantillas = plantillaServicio.obtenerPlantillasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(plantillas);
    }

}