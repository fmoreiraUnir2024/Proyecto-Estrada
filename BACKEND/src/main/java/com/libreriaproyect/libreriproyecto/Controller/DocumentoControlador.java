package com.libreriaproyect.libreriproyecto.Controller;


import com.libreriaproyect.libreriproyecto.Services.DocumentoServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/documentos")
public class DocumentoControlador {

    @Autowired
    private DocumentoServicio documentoServicio;

    @PostMapping
    public ResponseEntity<Documento> crearDocumento(@RequestBody Documento documento) {
        Documento nuevoDocumento = documentoServicio.crearDocumento(documento);
        return ResponseEntity.ok(nuevoDocumento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obtenerDocumentoPorId(@PathVariable Long id) {
        Documento documento = documentoServicio.obtenerDocumentoPorId(id);
        if (documento != null) {
            return ResponseEntity.ok(documento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Documento>> obtenerTodosLosDocumentos() {
        List<Documento> documentos = documentoServicio.obtenerTodosLosDocumentos();
        return ResponseEntity.ok(documentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> actualizarDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        Documento documentoActualizado = documentoServicio.actualizarDocumento(id, documento);
        if (documentoActualizado != null) {
            return ResponseEntity.ok(documentoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long id) {
        documentoServicio.eliminarDocumento(id);
        return ResponseEntity.noContent().build();
    }
}