package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.repository.RolRepository;
import com.libreriaproyect.libreriproyecto.Services.RolService;
import com.libreriaproyect.libreriproyecto.entitys.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplRolService implements RolService {
    @Autowired
    private RolRepository rolRepository;
    @Override
    public Rol obtenerRol(String nombreRol) {
        Rol rol= rolRepository.findByNombre(nombreRol);
        return rol;
    }
}
