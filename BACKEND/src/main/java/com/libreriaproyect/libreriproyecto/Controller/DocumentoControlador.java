package com.libreriaproyect.libreriproyecto.Controller;


import com.libreriaproyect.libreriproyecto.Services.DocumentoServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import com.libreriaproyect.libreriproyecto.entidades.documento.dto.DocumentoDTO;
import com.libreriaproyect.libreriproyecto.entidades.documento.dto.FuenteConocimientoDTO;
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
    public ResponseEntity<Documento> crearDocumento(@RequestBody DocumentoDTO documento) {
        Documento nuevoDocumento = documentoServicio.crearDocumento(documento);
        return ResponseEntity.ok(nuevoDocumento);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<DocumentoDTO>> obtenerDocumentoPorProyecto(@PathVariable Integer id) {
        List<DocumentoDTO> documentos = documentoServicio.obtenerTodosLosDocumentosPorProyecto(id);
        if (documentos != null) {
            return ResponseEntity.ok(documentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public static FuenteConocimientoDTO buildFromDocuments(List<DocumentoDTO> documentos) {
        StringBuilder contenidoBuilder = new StringBuilder();

        documentos.forEach(documento -> {
            contenidoBuilder.append("Título: ").append(documento.getNombre()).append("\n");
            contenidoBuilder.append("Contenido: ").append(documento.getContenido()).append("\n\n");
        });

        String contenido = contenidoBuilder.toString();

        return FuenteConocimientoDTO.builder()
                .cantidad(documentos.stream().count())
                .contenido(contenido
                        .replace("\n", "")
                        .replace("\r", "").replace("\r\n"," "))
                .build();
    }
    @GetMapping("analisis/{id}")
    public FuenteConocimientoDTO fuenteDeconocimiento(@PathVariable Integer id) {
        List<DocumentoDTO> documentos = documentoServicio.obtenerTodosLosDocumentosPorProyecto(id);
         FuenteConocimientoDTO fuenteConocimientoDTO = buildFromDocuments(documentos);
        return fuenteConocimientoDTO;
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