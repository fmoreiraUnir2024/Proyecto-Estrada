package com.libreriaproyect.libreriproyecto.Services;

import com.libreriaproyect.libreriproyecto.entitys.UserRol;
import com.libreriaproyect.libreriproyecto.entitys.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public User guardarUsuario(User usuario, Set<UserRol> userRolSet) throws Exception;
    public User ObtenerUsuario(String username);
    public Boolean eliminarUsuario(Long usuarioId);
    public List<User> ListUser();
}
