package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;

import java.util.List;

public interface DocumentoServicio {
    Documento crearDocumento(Documento documento);
    Documento obtenerDocumentoPorId(Long id);
    List<Documento> obtenerTodosLosDocumentos();
    Documento actualizarDocumento(Long id, Documento documento);
    void eliminarDocumento(Long id);
}
