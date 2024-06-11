package com.libreriaproyect.libreriproyecto.Controller;

import com.libreriaproyect.libreriproyecto.entidades.usuario.UsuarioRol;
import com.libreriaproyect.libreriproyecto.Services.RolService;
import com.libreriaproyect.libreriproyecto.Services.UserService;
import com.libreriaproyect.libreriproyecto.entidades.usuario.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("user/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController  {
   @Autowired
   private  UserService userService;
   @Autowired
   private RolService rolService;
   @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/save")
    public Usuario GuardarUsuario(@RequestBody Usuario user) throws Exception {

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rolService.obtenerRol("ADMIN"));
        usuarioRol.setUsuario(user);
        usuarioRoles.add(usuarioRol);

      return userService.guardarUsuario(user, usuarioRoles);
    }

    @GetMapping(" /(username}")
    @Procedure(MediaType.APPLICATION_JSON_VALUE)
    public Usuario obtenerUsuario(@PathVariable("username") String username) {
        return userService.ObtenerUsuario(username);
    }

    @GetMapping(" /(usuarioId}")
    public Boolean eliminarUserbyId(@PathVariable("usuarioId") Long userID) {
        return userService.eliminarUsuario(userID);
    }

}
