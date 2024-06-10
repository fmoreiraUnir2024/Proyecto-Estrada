package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.Services.DocumentoServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import com.libreriaproyect.libreriproyecto.repository.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DocumentoServicioImpl implements DocumentoServicio {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Override
    public Documento crearDocumento(Documento documento) {
        return documentoRepositorio.save(documento);
    }

    @Override
    public Documento obtenerDocumentoPorId(Long id) {
        Optional<Documento> documento = documentoRepositorio.findById(id);
        return documento.orElse(null);
    }

    @Override
    public List<Documento> obtenerTodosLosDocumentos() {
        return documentoRepositorio.findAll();
    }

    @Override
    public Documento actualizarDocumento(Long id, Documento documento) {
        if (documentoRepositorio.existsById(id)) {
            documento.setId(id);
            return documentoRepositorio.save(documento);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarDocumento(Long id) {
        documentoRepositorio.deleteById(id);
    }
}