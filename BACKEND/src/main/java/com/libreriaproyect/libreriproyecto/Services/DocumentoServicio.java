package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import com.libreriaproyect.libreriproyecto.entidades.documento.dto.DocumentoDTO;

import java.util.List;

public interface DocumentoServicio {
    Documento crearDocumento(DocumentoDTO documento);
    Documento obtenerDocumentoPorId(Long id);
    List<DocumentoDTO> obtenerTodosLosDocumentosPorProyecto(Integer id);
    List<Documento> obtenerTodosLosDocumentos();
    Documento actualizarDocumento(Long id, Documento documento);
    void eliminarDocumento(Long id);
}
