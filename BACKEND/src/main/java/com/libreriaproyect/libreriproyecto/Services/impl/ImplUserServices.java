package com.libreriaproyect.libreriproyecto.Services.impl;

import com.libreriaproyect.libreriproyecto.entidades.usuario.UsuarioRol;
import com.libreriaproyect.libreriproyecto.repository.RolRepository;
import com.libreriaproyect.libreriproyecto.repository.UserRepository;
import com.libreriaproyect.libreriproyecto.Services.UserService;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class ImplUserServices implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolRepository rolRepository;
    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> userRolSet) throws Exception {
        Usuario user= userRepository.findByUsername(usuario.getUsername());
        if(user!=null)
        {
            System.out.println("El usuario ya exite");
            throw  new Exception("El usuario ya existe");
        }else {
            for(UsuarioRol usuarioRol:userRolSet)
            {
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUserRolSet().addAll(userRolSet);
            user = userRepository.save(usuario);
        }
        return user;
    }

    @Override
    public Usuario ObtenerUsuario(String username) {
        return  userRepository.findByUsername(username);
    }
    @Override
    public Boolean eliminarUsuario(Long usuarioId) {
        userRepository.deleteById(usuarioId);
        return userRepository.existsById(usuarioId);
    }

    @Override
    public List<Usuario> ListUser() {
        List<Usuario>  list= userRepository.findAll();
        return list;
    }
}
