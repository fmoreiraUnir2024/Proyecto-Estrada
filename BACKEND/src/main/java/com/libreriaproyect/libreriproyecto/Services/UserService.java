package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entidades.usuario.UsuarioRol;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;

import java.util.List;
import java.util.Set;

public interface UserService {
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> userRolSet) throws Exception;
    public Usuario ObtenerUsuario(String username);
    public Boolean eliminarUsuario(Long usuarioId);
    public List<Usuario> ListUser();
}
