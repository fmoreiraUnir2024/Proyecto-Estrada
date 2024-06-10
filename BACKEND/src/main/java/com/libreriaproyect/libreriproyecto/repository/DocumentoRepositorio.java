package com.libreriaproyect.libreriproyecto.repository;


import com.libreriaproyect.libreriproyecto.entidades.documento.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {

}