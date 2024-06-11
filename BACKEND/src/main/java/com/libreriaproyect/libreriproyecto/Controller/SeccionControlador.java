package com.libreriaproyect.libreriproyecto.Controller;
import com.libreriaproyect.libreriproyecto.Services.SeccionServicio;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Seccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secciones")
@CrossOrigin(origins = "http://localhost:4200")
public class SeccionControlador {

    @Autowired
    private SeccionServicio seccionServicio;

    @PostMapping
    public ResponseEntity<Seccion> crearSeccion(@RequestBody Seccion seccion) {
        Seccion nuevaSeccion = seccionServicio.crearSeccion(seccion);
        return ResponseEntity.ok(nuevaSeccion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seccion> obtenerSeccionPorId(@PathVariable Long id) {
        Seccion seccion = seccionServicio.obtenerSeccionPorId(id);
        if (seccion != null) {
            return ResponseEntity.ok(seccion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Seccion>> obtenerTodasLasSecciones() {
        List<Seccion> secciones = seccionServicio.obtenerTodasLasSecciones();
        return ResponseEntity.ok(secciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seccion> actualizarSeccion(@PathVariable Long id, @RequestBody Seccion seccion) {
        Seccion seccionActualizada = seccionServicio.actualizarSeccion(id, seccion);
        if (seccionActualizada != null) {
            return ResponseEntity.ok(seccionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeccion(@PathVariable Long id) {
        seccionServicio.eliminarSeccion(id);
        return ResponseEntity.noContent().build();
    }
}