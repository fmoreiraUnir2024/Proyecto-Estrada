package com.libreriaproyect.libreriproyecto.Controller;

import com.libreriaproyect.libreriproyecto.Services.RetroalimentacionServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Retroalimentacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retroalimentaciones")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RetroalimentacionControlador {

    @Autowired
    private RetroalimentacionServicio retroalimentacionServicio;

    @PostMapping
    public ResponseEntity<Retroalimentacion> crearRetroalimentacion(@RequestBody Retroalimentacion retroalimentacion) {
        Retroalimentacion nuevaRetroalimentacion = retroalimentacionServicio.crearRetroalimentacion(retroalimentacion);
        return ResponseEntity.ok(nuevaRetroalimentacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retroalimentacion> obtenerRetroalimentacionPorId(@PathVariable Long id) {
        Retroalimentacion retroalimentacion = retroalimentacionServicio.obtenerRetroalimentacionPorId(id);
        if (retroalimentacion != null) {
            return ResponseEntity.ok(retroalimentacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Retroalimentacion>> obtenerTodasLasRetroalimentaciones() {
        List<Retroalimentacion> retroalimentaciones = retroalimentacionServicio.obtenerTodasLasRetroalimentaciones();
        return ResponseEntity.ok(retroalimentaciones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Retroalimentacion> actualizarRetroalimentacion(@PathVariable Long id, @RequestBody Retroalimentacion retroalimentacion) {
        Retroalimentacion retroalimentacionActualizada = retroalimentacionServicio.actualizarRetroalimentacion(id, retroalimentacion);
        if (retroalimentacionActualizada != null) {
            return ResponseEntity.ok(retroalimentacionActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRetroalimentacion(@PathVariable Long id) {
        retroalimentacionServicio.eliminarRetroalimentacion(id);
        return ResponseEntity.noContent().build();
    }
}