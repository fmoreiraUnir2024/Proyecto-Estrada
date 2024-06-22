package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.Services.DocumentoServicio;
import com.libreriaproyect.libreriproyecto.Services.ProyectoServicio;
import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import com.libreriaproyect.libreriproyecto.entidades.documento.dto.DocumentoDTO;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Plantilla;
import com.libreriaproyect.libreriproyecto.entidades.proyecto.Proyecto;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import com.libreriaproyect.libreriproyecto.repository.DocumentoRepositorio;
import com.libreriaproyect.libreriproyecto.repository.ProyectoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentoServicioImpl implements DocumentoServicio {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Override
    public Documento crearDocumento(DocumentoDTO documentoDTO) {
        Proyecto proyecto =
                proyectoRepositorio.findById(documentoDTO.getProyecto_id())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Documento documento= new Documento();
        documento.setNombre(documentoDTO.getNombre());
        documento.setFormato(documentoDTO.getFormato());
        documento.setContenido(documentoDTO.getContenido());
        documento.setProyecto(proyecto);
        return documentoRepositorio.save(documento);
    }
    @Override
    public List<DocumentoDTO> obtenerTodosLosDocumentosPorProyecto(Integer id) {
        List<Documento> documentos = documentoRepositorio.findByProyectoId(id);
        return documentos.stream().map(this::convertirADTO).collect(Collectors.toList());

    }
    private DocumentoDTO convertirADTO(Documento documento) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(documento.getId());
        dto.setNombre(documento.getNombre());
        dto.setFormato(documento.getFormato());
        dto.setContenido(documento.getContenido());
        dto.setProyecto_id(documento.getProyecto().getId().intValue());
        return dto;
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